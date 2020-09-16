package com.library.common.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.androidadvance.topsnackbar.TSnackbar
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.R
import com.library.common.mvvm.BaseViewModel
import com.library.common.mvvm.IView
import com.library.common.view.IVaryViewHelperController
import com.library.common.view.LoadingDialog
import com.library.common.view.VaryViewHelperController
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.lang.reflect.ParameterizedType

/**
 * BaseDialogFragment弹窗
 *
 * @author yangbw
 * @date 2020/9/1
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseDialogFragment<VM : BaseViewModel<*>, DB : ViewDataBinding> :
    DialogFragment(), IView, SimpleImmersionOwner {

    //viewModel
    protected lateinit var mViewModel: VM

    //dataBinding
    protected lateinit var mBinding: DB

    /**
     * ImmersionBar代理类
     */
    private val mSimpleImmersionProxy = SimpleImmersionProxy(this)

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
     * 刷新相关 因为单界面不存在加载，这样只针对是否开启刷新功能做处理，可设置为null，为null则不具备刷新相关能力
     */
    abstract fun getSmartRefreshLayout(): SmartRefreshLayout?

    private var mRefreshEnable = true //是否能进行下拉刷新

    abstract fun refreshData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            return mBinding.root
        }
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createViewModel()
        mViewController = initVaryViewHelperController()
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
     *
     * actualTypeArguments[0]  BaseViewModel
     * actualTypeArguments[1]  ViewDataBinding
     *
     */
    private fun createViewModel() {
        //创建viewModel
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
            snackBarView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent))
            val textView =
                snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.m90EE90))
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
        mViewController?.showLoading()
    }

    override fun showLoading(msg: String?) {
        mViewController?.showLoading(msg)
    }

    override fun showEmpty(listener: View.OnClickListener?) {
        mViewController?.showEmpty(listener)
    }

    override fun showEmpty(
        emptyMsg: String?,
        listener: View.OnClickListener?
    ) {
        mViewController?.showEmpty(emptyMsg, listener)
    }

    override fun showNetworkError(listener: View.OnClickListener?) {
        mViewController?.showNetworkError(listener)
    }

    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        mViewController?.showNetworkError(msg, listener)
    }

    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        mViewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

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
        get() = requireActivity()

    override val mContext: Context
        get() = requireContext()

    override val mAppContext: Context
        get() = requireActivity().applicationContext


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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        @Suppress("DEPRECATION")
        super.setUserVisibleHint(isVisibleToUser)
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }

    /**
     * 沉浸式效果
     */
    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            statusBarColor(R.color.colorPrimary)
        }
    }

    override fun onDestroy() {
        mSimpleImmersionProxy.onDestroy()
        dismissDialog()
        super.onDestroy()
    }

}