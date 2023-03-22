package com.yangbw.libtest.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.RegexUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseActivity
import com.library.common.extension.getTextToString
import com.library.common.extension.isEmpty
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.common.Constant
import com.yangbw.libtest.databinding.ActivityNewPhoneBinding
import com.yangbw.libtest.ui.viewmodel.NewPhoneViewModel
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_new_phone.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * 验证新手机号
 *
 * @author :yangbw
 * @date :2020/9/22
 */
class NewPhoneActivity : BaseActivity<NewPhoneViewModel, ActivityNewPhoneBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, NewPhoneActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_new_phone

    override fun getReplaceView(): View = activity_new_phone

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
        ActionBarUtils.setToolBarTitleText(toolbar, getString(R.string.bind_new_phone))
        mBinding.run {
            tvPhone.text = UserInfoUtils.getUser()!!.phone
            setOnClickListener(tvSend, btnSubmit) {
                val phone = etPhone.getTextToString()
                if (TextUtils.isEmpty(phone)) {
                    showToast(etPhone.hint.toString())
                    return@setOnClickListener
                }
                if (!RegexUtils.isMobileExact(phone)) {
                    showToast(context.getString(R.string.phone_format_error))
                    return@setOnClickListener
                }
                when (tvSend) {
                    this -> {
                        mViewModel.sendCode(phone)
                    }
                    else -> {
                        if (etCode.isEmpty()) {
                            showToast(etCode.hint.toString())
                            return@setOnClickListener
                        }
                        mViewModel.changePhone(phone, etCode.getTextToString())
                    }
                }
            }
        }
        mViewModel.mSendCode.observe(this) {
            showToast(it)
            mCountDownTimer.start()
        }
        mViewModel.mChangePhone.observe(this) {
            showToast(it)
            finish()
        }
    }

    private val mCountDownTimer = object : CountDownTimer(Constant.TIME_CODE, Constant.SECOND) {
        override fun onFinish() {
            mBinding.tvSend.apply {
                text = mContext.getString(R.string.resend_code)
                setTextColor(ContextCompat.getColor(mContext, R.color.main_color))
                isEnabled = true
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            mBinding.tvSend.apply {
                text = String.format(
                    mContext.getString(R.string.change_send_code_able),
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
