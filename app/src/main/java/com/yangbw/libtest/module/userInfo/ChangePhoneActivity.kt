package com.yangbw.libtest.module.userInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseActivity
import com.library.common.extension.getTextToString
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityChangePhoneBinding
import com.yangbw.libtest.utils.CommonUtils
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_change_phone.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * 验证原手机号
 *
 * @author :yangbw
 * @date :2020/9/22
 */
class ChangePhoneActivity : BaseActivity<ChangePhoneViewModel, ActivityChangePhoneBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, ChangePhoneActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_change_phone

    override fun getReplaceView(): View = activity_change_phone

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            statusBarColor(com.library.common.R.color.white)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setSupportActionBarWithBack(toolbar, R.mipmap.icon_common_back_black) {
            finish()
        }
        ActionBarUtils.setToolBarTitleText(toolbar, getString(R.string.check_old_phone))
        mBinding.run {
            UserInfoUtils.getUser()?.let {
                tvPhone.text = it.phone
            }
            CommonUtils.setTextColorClick(mContext, tvServer, 14, 18, R.color.main_color,
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        showToast(R.string.service_tips)
                    }
                })
            etCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    btnCheck.isEnabled = s.toString().length == 4
                }
            })
            setOnClickListener(tvSend, tvVoice, btnCheck) {
                when (this) {
                    tvSend -> {
                        mViewModel.sendCode()
                    }
                    tvVoice -> {
                        showToast(R.string.fun_no_support)
                    }
                    btnCheck -> {
                        mViewModel.verifyCode(etCode.getTextToString())
                    }
                }
            }
        }

        mViewModel.mSendCode.observe(this) {
            mCountDownTimer.start()
        }
        mViewModel.mVerifyCode.observe(this) {
            NewPhoneActivity.launch(mContext)
            finish()
        }
    }

    private val mCountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            mBinding.tvSend.apply {
                text = context.getString(R.string.resend_code)
                setTextColor(ContextCompat.getColor(mContext, R.color.main_color))
                isEnabled = true
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            mBinding.tvSend.apply {
                text = String.format(
                    context.getString(R.string.change_send_code_able),
                    millisUntilFinished / 1000
                )
                setTextColor(ContextCompat.getColor(mContext, R.color.text_gray))
                isEnabled = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }

}
