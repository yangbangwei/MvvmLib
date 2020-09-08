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
import androidx.lifecycle.ViewModelProviders
import com.androidadvance.topsnackbar.TSnackbar
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.library.common.R
import com.library.common.mvvm.BaseViewModel
import com.library.common.mvvm.IView
import com.library.common.utils.ActivityUtils
import com.library.common.view.IVaryViewHelperController
import com.library.common.view.LoadingDialog
import com.library.common.view.VaryViewHelperController
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.lang.reflect.ParameterizedType

/**
 * BaseActivity封装
 *
 * @author yangbw
 * @date 2020/3/16.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<VM : BaseViewModel<*>, DB : ViewDataBinding> : AppCompatActivity(),
    IView {

    /**
     * viewModel
     */
    protected lateinit var mViewModel: VM

    /**
     * dataBing
     */
    protected lateinit var mBinding: DB

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
    open fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    private var mRefreshEnable = true //是否能进行下拉刷新

    open fun refreshData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityUtils.get()?.addActivity(this)
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        createViewModel()
        viewController = initVaryViewHelperController()
        lifecycle.addObserver(mViewModel)
        registerViewChange()
        initBar()
        initRefresh()
        init(savedInstanceState)
    }

    /***
     * view
     */
    protected open fun initVaryViewHelperController(): IVaryViewHelperController {
        return VaryViewHelperController(getReplaceView())
    }

    private fun initBar() {
        ImmersionBar.with(this)
            .autoStatusBarDarkModeEnable(true)
            .fitsSystemWindows(true)
            .statusBarColor(getStatusBarColor())
            .init()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    open fun getStatusBarColor() = R.color.colorPrimary

    private fun initRefresh() {
        getSmartRefreshLayout()?.run {
            isEnabled = mRefreshEnable
            setEnableLoadMore(false)
            if (mRefreshEnable) {
                setOnRefreshListener {
                    refreshData()
                }
            }
        }
    }

    /**
     *     DataBinding
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *
     */
    private fun initViewDataBinding() {
        val cls: Class<*> = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
            mBinding.lifecycleOwner = this
        } else {
            setContentView(getLayoutId())
        }

    }

    /**
     *     创建viewmodel
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *
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
     * 注册视图变化事件
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
            getSmartRefreshLayout()?.finishRefresh()
        })
    }

    /**
     * 展示弹窗
     */
    override fun showDialogProgress(msg: String) {
        showDialogProgress(msg, true)
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
        snackBarView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mCCE4FF))
        val textView =
            snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.m177AE6))
        snackBar.show()
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
        viewController?.showLoading()
    }

    /***
     * loading 带文字
     */
    override fun showLoading(msg: String?) {
        viewController?.showLoading(msg)
    }

    /**
     * 无数据，空白页
     */
    override fun showEmpty(listener: View.OnClickListener?) {
        viewController?.showEmpty(listener)
    }

    /**
     * 无数据，空白页
     */
    override fun showEmpty(emptyMsg: String?, listener: View.OnClickListener?) {
        viewController?.showEmpty(emptyMsg, listener)
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(listener: View.OnClickListener?) {
        viewController?.showNetworkError(listener)
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(msg: String?, listener: View.OnClickListener?) {
        viewController?.showNetworkError(msg, listener)
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
        viewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

    /**
     * 恢复
     */
    override fun restore() {
        viewController?.restore()
    }

    override val isHasRestore: Boolean
        get() = viewController?.isHasRestore ?: false

    /**
     * toast
     */
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
        ActivityUtils.get()?.remove(this)
        super.onDestroy()
    }
}