package com.library.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.androidadvance.topsnackbar.TSnackbar
import com.blankj.utilcode.util.ToastUtils
import com.library.common.R
import com.library.common.mvvm.BaseViewModel
import com.library.common.mvvm.IView
import com.library.common.view.IVaryViewHelperController
import com.library.common.view.LoadingDialog
import com.library.common.view.VaryViewHelperController
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.lang.reflect.ParameterizedType

/**
 * BaseFragment封装
 *
 * @author yangbw
 * @date 2020/9/1
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel<*>, DB : ViewDataBinding> : Fragment(),
    IView {

    //viewModel
    protected lateinit var mViewModel: VM

    //databing
    protected var mBinding: DB? = null

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
    private var viewController: IVaryViewHelperController? = null

    /**
     * 弹窗
     */
    private var mLoadingDialog: LoadingDialog? = null

    /**
     * 刷新相关 因为单界面不存在加载，这样只针对是否开启刷新功能做处理，可设置为null，为null则不具备刷新相关能力
     */
    open fun getSmartRefreshLayout(): SmartRefreshLayout? = null

    private var mRefreshEnable = true //是否能进行下拉刷新

    open fun refreshData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            return mBinding?.root
        }
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createViewModel()
        viewController = initVaryViewHelperController()
        lifecycle.addObserver(mViewModel)
        //注册 UI事件
        registerViewChange()
        initRefresh()
        init(savedInstanceState)
    }

    /***
     * view
     */
    protected open fun initVaryViewHelperController(): IVaryViewHelperController? {
        return VaryViewHelperController(getReplaceView())
    }

    /**
     * 刷新
     */
    private fun initRefresh() {
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout()?.isEnabled = mRefreshEnable
            //不具备加载功能
            getSmartRefreshLayout()?.setEnableLoadMore(false)
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.setOnRefreshListener {
                    refreshData()
                }
            }
        }
    }

    /**
     *  创建viewModel
     *  actualTypeArguments[0]  BaseViewModel
     *  actualTypeArguments[1]  ViewDataBinding
     */
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProviders.of(this)[tClass] as VM
        }
    }

    /**
     * 注册 UI 事件
     */
    private fun registerViewChange() {
        mViewModel.viewState.showLoading.observe(this, {
            viewController?.let {
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
            viewController?.restore()
            //代表有设置刷新
            if (getSmartRefreshLayout() != null) {
                getSmartRefreshLayout()?.finishRefresh()
            }
        })
    }

    /**
     * 相关view替换
     */
    override fun showTips(msg: String) {
        activity?.let {
            val snackBar = TSnackbar.make(
                it.findViewById(android.R.id.content),
                msg,
                TSnackbar.LENGTH_SHORT
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorAccent))
            val textView =
                snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
            textView.setTextColor(ContextCompat.getColor(mContext,R.color.m90EE90))
            snackBar.show()
        }
    }

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

    override fun dismissDialog() {
        mLoadingDialog?.let {
            if (it.isShowing) it.cancel()
        }
    }

    override fun showLoading() {
        viewController?.showLoading()
    }

    override fun showLoading(msg: String?) {
        viewController?.showLoading(msg)
    }

    override fun showEmpty(listener: View.OnClickListener?) {
        viewController?.showEmpty(listener)
    }

    override fun showEmpty(
        emptyMsg: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showEmpty(emptyMsg, listener)
    }

    override fun showNetworkError(listener: View.OnClickListener?) {
        viewController?.showNetworkError(listener)
    }

    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showNetworkError(msg, listener)
    }

    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

    override fun restore() {
        viewController?.restore()
    }

    override val isHasRestore: Boolean
        get() = viewController?.isHasRestore ?: false

    override fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun showToast(msg: Int) {
        ToastUtils.showShort(msg)
    }

    override val mActivity: Activity
        get() = activity!!

    override val mContext: Context
        get() = context!!

    override val mAppContext: Context
        get() = activity!!.applicationContext

    /**
     *  @param refreshEnable 设置是否刷新操作
     */
    open fun setRefreshEnable(refreshEnable: Boolean) {
        //不为空才可以刷新
        if (getSmartRefreshLayout() != null) {
            mRefreshEnable = refreshEnable
            getSmartRefreshLayout()?.isEnabled = mRefreshEnable
        }
    }

    override fun onDestroy() {
        dismissDialog()
        super.onDestroy()
    }

}