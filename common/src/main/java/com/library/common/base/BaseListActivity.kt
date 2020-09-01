package com.library.common.base

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.androidadvance.topsnackbar.TSnackbar
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ImmersionBar
import com.library.common.R
import com.library.common.mvvm.BaseListViewModel
import com.library.common.mvvm.IListView
import com.library.common.utils.ActivityUtils
import com.library.common.utils.ListUtils
import com.library.common.view.IVaryViewHelperController
import com.library.common.view.LoadingDialog
import com.library.common.view.VaryViewHelperController
import com.library.common.view.baseviewholder.CommonViewHolder
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.lang.reflect.ParameterizedType

/**
 * BaseListActivity封装
 *
 * @author yangbw
 * @date 2020/9/1
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseListActivity<VM : BaseListViewModel<*>, DB : ViewDataBinding,
        A : BaseQuickAdapter<T, CommonViewHolder>, T> : AppCompatActivity(),
    IListView<T> {

    protected lateinit var mViewModel: VM
    protected var mBinding: DB? = null
    protected var mAdapter: A? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * @return 该View 替换为显示loadingView 或者 emptyView 或者 netWorkErrorView
     */
    abstract fun getReplaceView(): View

    /**
     * 初始化
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 替换view
     */
    private var mViewController: IVaryViewHelperController? = null

    /**
     * 弹窗
     */
    private var mLoadingDialog: LoadingDialog? = null

    /**
     * list展示相关
     */
    private var mPageNum = 1
    private var mLoadMoreEnable = true
    private var mLoadPageNum = 1 //当前正在加载的page，但是当前page接口还未做出响应
    private var mRefreshEnable = true //是否能进行下拉刷新

    abstract fun loadPageListData(pageNo: Int)

    abstract fun getSmartRefreshLayout(): SmartRefreshLayout?

    abstract fun getRecyclerView(): RecyclerView?

    abstract fun getAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityUtils.get()?.addActivity(this)
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        createViewModel()
        mViewController = initVaryViewHelperController()
        lifecycle.addObserver(mViewModel)
        registerViewChange()
        initRefreshLoadMore()
        initBar()
        init(savedInstanceState)
        registerDataChange()
    }

    /***
     * view
     */
    protected open fun initVaryViewHelperController(): IVaryViewHelperController? {
        return VaryViewHelperController(getReplaceView())
    }

    /**
     * DataBinding
     * actualTypeArguments[0]  BaseListViewModel
     * actualTypeArguments[1]  ViewDataBinding
     * actualTypeArguments[2]  T
     *
     */
    private fun initViewDataBinding() {
        val cls: Class<*> =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
            mBinding?.lifecycleOwner = this
        } else {
            setContentView(getLayoutId())
        }

    }

    /**
     *     创建viewmodel
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *     actualTypeArguments[2]  T
     *
     */
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseListViewModel::class.java
            mViewModel = ViewModelProviders.of(this)[tClass] as VM
        }
    }

    /**
     * 注册视图变化事件
     */
    private fun registerViewChange() {
        mViewModel.viewState.showLoading.observe(this, {
            mViewController?.let {
                if (!it.isHasRestore) {
                    showLoading()
                }
            }
        })
        mViewModel.viewState.showDialogProgress.observe(this, {
            showDialogProgress(it)
        })
        mViewModel.viewState.dismissDialog.observe(this, {
            dismissDialog()
        })
        mViewModel.viewState.showToast.observe(this, {
            showToast(it)
        })
        mViewModel.viewState.showTips.observe(this, {
            showTips(it)
        })
        mViewModel.viewState.showEmpty.observe(this, {
            showEmpty(it, mViewModel.listener)
        })
        mViewModel.viewState.showNetworkError.observe(this, {
            showNetworkError(it, mViewModel.listener)
        })
        mViewModel.viewState.restore.observe(this, {
            mViewController?.restore()
        })
        mViewModel.viewState.refreshComplete.observe(this, {
            refreshComplete()
        })
    }

    /**
     * 接口请求的数据变化
     */
    private fun registerDataChange() {
        mViewModel.mResult = MutableLiveData<T>()
        //数据变化的监听
        mViewModel.mResult?.observe(this, {
            showListData(it as MutableList<T>, mPageNum)
        })
    }


    /**
     * 相关view替换
     */

    override fun showTips(msg: String) {
        val snackBar = TSnackbar.make(
            findViewById(android.R.id.content),
            msg,
            TSnackbar.LENGTH_SHORT
        )
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.mCCE4FF))
        val textView =
            snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.m177AE6))
        snackBar.show()
    }

    /**
     * 展示弹窗
     */
    override fun showDialogProgress(msg: String) {
        showDialogProgress(msg, true)
    }

    override fun showDialogProgress(msg: String, cancelable: Boolean) {
        try {
            if (mLoadingDialog == null) {
                mLoadingDialog = LoadingDialog(mContext)
            }
            mLoadingDialog?.show(msg, cancelable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 消失
     */
    override fun dismissDialog() {
        mLoadingDialog?.let {
            if (it.isShowing) it.cancel()
        }
    }

    /**
     * loading
     */
    override fun showLoading() {
        mViewController?.showLoading()
    }

    /***
     * loading 带文字
     */
    override fun showLoading(msg: String?) {
        mViewController?.showLoading(msg)
    }

    /**
     * 无数据，空白页
     */
    override fun showEmpty(listener: View.OnClickListener?) {
        //加载当前page 出现List 为empty的情况--需要判断是否是第一页或者不是第一页
        mViewController?.showEmpty(listener)
    }

    override fun showEmpty(emptyMsg: String?, listener: View.OnClickListener?) {
        //加载当前page 出现List 为empty的情况--需要判断是否是第一页或者不是第一页
        if (mLoadPageNum > 1) {
            showNoMore()
        } else {
            mPageNum = 1
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.isEnabled = true
            }
            getSmartRefreshLayout()?.finishRefresh()
            mAdapter?.data?.clear()
            mViewController?.showEmpty(emptyMsg, listener)
        }
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(listener: View.OnClickListener?) {
        mViewController?.showNetworkError(listener)
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        if (mLoadPageNum > 1) {
            showLoadMoreError()
        } else {
            mPageNum = 1
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.isEnabled = true
            }
            getSmartRefreshLayout()?.finishRefresh()
            mAdapter?.data?.clear()
            mViewController?.showNetworkError(msg, listener)
        }
    }

    /**
     * 自定义view展示
     */
    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        mViewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

    /**
     * 恢复
     */
    override fun restore() {
        mViewController?.restore()
    }

    override val isHasRestore: Boolean
        get() = mViewController?.isHasRestore ?: false

    override fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun showToast(msg: Int) {
        ToastUtils.showShort(msg)
    }

    override val mActivity: Activity
        get() = this

    override val mContext: Context
        get() = this

    override val mAppContext: Context
        get() = applicationContext

    /**
     *
     * @param moreEnable 设置是否加载更多操作
     */
    open fun setLoadMoreEnable(moreEnable: Boolean) {
        mLoadMoreEnable = moreEnable
        getSmartRefreshLayout()?.setEnableLoadMore(mLoadMoreEnable)
    }

    /**
     *  @param refreshEnable 设置是否刷新操作
     */
    open fun setRefreshEnable(refreshEnable: Boolean) {
        mRefreshEnable = refreshEnable
        getSmartRefreshLayout()?.isEnabled = mRefreshEnable
    }

    private fun initBar() {
        ImmersionBar.with(this)
            .autoStatusBarDarkModeEnable(true)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.colorPrimary)
            .init()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 设置刷新加载相关
     */
    private fun initRefreshLoadMore() {
        getAdapter()
        //设置相关设置
        getRecyclerView()?.adapter = mAdapter
        getSmartRefreshLayout()?.isEnabled = mRefreshEnable
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.setOnRefreshListener {
                mLoadPageNum = 1
                mPageNum = 1
                loadPageListData(mPageNum)
            }
        }
        if (mLoadMoreEnable) {
            getSmartRefreshLayout()?.setOnLoadMoreListener {
                mLoadPageNum = mPageNum + 1
                mPageNum += 1
                loadPageListData(mPageNum)
            }
        }
    }

    /**
     * 自动刷新
     */
    open fun autoRefresh() {
        if (ListUtils.getCount(mAdapter?.data) > 0) {
            getSmartRefreshLayout()?.autoRefresh()
        } else {
            showLoading()
            loadPageListData(1)
        }
    }

    /**
     * 数据展示
     */
    override fun showListData(datas: List<T>?, pageNum: Int) {
        this.mPageNum = pageNum
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.isEnabled = true
        }
        if (pageNum == 1) {
            getSmartRefreshLayout()?.finishRefresh()
            mAdapter?.setNewData(datas as MutableList<T>)
        } else {
            getSmartRefreshLayout()?.finishLoadMore()
            datas?.let {
                mAdapter?.addData(it)
            }
        }
    }

    /**
     * 加载更多---没有更多了
     */
    open fun showNoMore() {
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.isEnabled = true
        }
        getSmartRefreshLayout()?.finishLoadMoreWithNoMoreData()
    }

    /**
     * 加载更多--当前page发生网络错误
     */
    open fun showLoadMoreError() {
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.isEnabled = true
        }
        getSmartRefreshLayout()?.finishLoadMore(false)
    }

    /**
     * 刷新完成
     */
    override fun refreshComplete() {
        getSmartRefreshLayout()?.finishLoadMore()
        getSmartRefreshLayout()?.finishRefresh()
    }

    override fun onDestroy() {
        dismissDialog()
        ActivityUtils.get()?.remove(this)
        super.onDestroy()
    }

}