package com.library.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.ToastUtils
import com.library.common.R
import com.library.common.mvvm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * BaseDialogFragment弹窗
 *
 * @author yangbw
 * @date 2020/9/1
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseDialogFragment<VM : BaseViewModel<*>, DB : ViewDataBinding> :
    DialogFragment() {

    /**
     * viewModel
     */
    protected lateinit var mViewModel: VM

    /**
     * dataBinding
     */
    protected lateinit var mBinding: DB

    protected val mActivity: Activity
        get() = requireActivity()

    protected val mContext: Context
        get() = requireContext()

    protected val mAppContext: Context
        get() = requireActivity().applicationContext

    /**
     * 是否支持点击关闭
     */
    protected var mIsCancelable = false

    /**
     * 背景透明度 0.0 - 1.0
     */
    protected var mDimAmount = 0.4f

    /**
     * 显示位置
     */
    protected var mGravity = Gravity.CENTER

    /**
     * 弹窗样式
     */
    protected var mWindowAnimations = R.style.CustomDialog

    /**
     * 宽度 0.0 - 1.0
     */
    protected var mWidth = 1f

    /**
     * 高度 0.0 - 1.0
     */
    protected var mHeight = 1f

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 初始化
     */
    abstract fun init(savedInstanceState: Bundle?)

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
        lifecycle.addObserver(mViewModel)
        init(savedInstanceState)

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

    override fun onStart() {
        super.onStart()
        dialog?.let {
            // 下面这些设置必须在此方法(onStart())中才有效
            val window = it.window
            // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            // 设置动画
            window?.setWindowAnimations(mWindowAnimations)
            val params = window?.attributes
            //背景透明
            params?.dimAmount = mDimAmount
            params?.gravity = mGravity
            // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
            params?.width = (resources.displayMetrics.widthPixels * mWidth).toInt()
            params?.height = (resources.displayMetrics.heightPixels * mHeight).toInt()
            window?.attributes = params
            //是否能关闭
            isCancelable = mIsCancelable
        }
    }

    protected fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

    protected fun showToast(msg: Int) {
        ToastUtils.showShort(msg)
    }

}