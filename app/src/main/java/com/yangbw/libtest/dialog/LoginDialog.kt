package com.yangbw.libtest.dialog

import android.os.Bundle
import com.library.common.base.BaseDialogFragment
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.common.CommonViewModel
import com.yangbw.libtest.databinding.DialogLoginBinding
import com.yangbw.libtest.utils.CommonUtils

/**
 *
 * 登录协议弹窗
 *
 * @author :yangbw
 * @date :2020/9/17
 */
class LoginDialog : BaseDialogFragment<CommonViewModel, DialogLoginBinding>() {

    companion object {
        fun newInstance() = LoginDialog()
    }

    override fun getLayoutId() = R.layout.dialog_login

    override fun init(savedInstanceState: Bundle?) {
        //基本配置
        mWidth = 0.85f
        //界面相关
        mBinding.run {
            //协议点击事件
            CommonUtils.setAgreementTxt(mContext, tvAgreement, R.color.main_color, true)
            //点击事件
            setOnClickListener(tvAgree, tvDisagree) {
                when (this) {
                    tvDisagree -> dismiss()
                    tvAgree -> {
                        onOkClick?.let { it1 -> it1() }
                        dismiss()
                    }
                }
            }
        }
    }

    private var onOkClick: (() -> Unit)? = null

    fun setOnOkClickListener(onOkClick: () -> Unit) {
        this.onOkClick = onOkClick
    }
}