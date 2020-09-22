package com.yangbw.libtest.dialog

import android.os.Bundle
import com.library.common.base.BaseDialogFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.common.CommonViewModel
import com.yangbw.libtest.databinding.DialogLogoutBinding

/**
 * 注销弹窗
 * @author :yangbw
 * @date :2020/9/22
 */
class LogoutDialog : BaseDialogFragment<CommonViewModel, DialogLogoutBinding>() {

    companion object {
        fun newInstance() = LogoutDialog()
    }

    override fun getLayoutId() = R.layout.dialog_logout

    override fun init(savedInstanceState: Bundle?) {
        //基本配置
        mIsCancelable = true
        mWidth = 0.9f
        mWindowAnimations = R.style.CustomDialog
        //点击事件
        mBinding.run {
            tvOk.setOnClickListener {
                dismiss()
            }
            tvCancel.setOnClickListener {
                dismiss()
            }
            cbRead.setOnCheckedChangeListener { _, isChecked ->
                tvOk.isEnabled = isChecked
            }
        }
    }

}