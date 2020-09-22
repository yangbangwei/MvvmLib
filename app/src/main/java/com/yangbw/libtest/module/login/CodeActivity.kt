package com.yangbw.libtest.module.login

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.*
import android.text.style.StyleSpan
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.databinding.ActivityCodeBinding
import com.yangbw.libtest.utils.CommonUtils
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_code.*


/**
 * 验证码
 *
 * @author :yangbw
 * @date :2020/9/18
 */
class CodeActivity : BaseActivity<CodeViewModel, ActivityCodeBinding>() {

    companion object {
        private const val PHONE = "phone"

        fun launch(context: Context, phone: String) {
            context.startActivity(Intent().apply {
                putExtra(PHONE, phone)
                setClass(context, CodeActivity::class.java)
            })
        }
    }

    private lateinit var mPhone: String

    override fun getLayoutId() = R.layout.activity_code

    override fun getReplaceView(): View = activity_code

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            statusBarColor(com.library.common.R.color.white)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mPhone = intent.getStringExtra(PHONE)!!
        mBinding.run {
            //点击事件
            setOnClickListener(ivClose, ivService, tvTips, btnLogin, group) {
                when (this) {
                    ivClose -> finish()
                    ivService -> showToast(context.getString(R.string.service_tips))
                    tvTips -> {
                        mViewModel.sendCode(mPhone)
                    }
                    btnLogin -> {
                        mViewModel.login(
                            mPhone, etNum1.text.toString() + etNum2.text.toString() +
                                    etNum3.text.toString() + etNum4.text.toString()
                        )
                    }
                    group -> {
                        CommonUtils.showOrHide(mContext)
                    }
                }
            }
            //发送的手机号
            val spannableString =
                SpannableString(String.format(getString(R.string.code_send_to), mPhone))
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                7,
                18,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvPhone.text = spannableString
            //验证码
            etNum1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        etNum2.requestFocus()
                    }
                }

            })
            etNum2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        etNum3.requestFocus()
                    }
                }

            })
            etNum3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        etNum4.requestFocus()
                    }
                }

            })
            etNum4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        btnLogin.isEnabled = true
                        CommonUtils.showOrHide(mContext)
                        mViewModel.login(
                            mPhone, etNum1.text.toString() + etNum2.text.toString() +
                                    etNum3.text.toString() + etNum4.text.toString()
                        )
                    }
                }

            })

            etNum2.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (TextUtils.isEmpty(etNum2.text.toString())) {
                        etNum1.requestFocus()
                        etNum1.text.clear()
                    } else {
                        etNum2.text.clear()
                    }
                }
                true
            }
            etNum3.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (TextUtils.isEmpty(etNum3.text.toString())) {
                        etNum2.text.clear()
                        etNum2.requestFocus()
                    } else {
                        etNum3.text.clear()
                    }
                }
                true
            }
            etNum4.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (TextUtils.isEmpty(etNum4.text.toString())) {
                        btnLogin.isEnabled = false
                        etNum3.text.clear()
                        etNum3.requestFocus()
                    } else {
                        etNum4.text.clear()
                    }
                }
                true
            }

        }
        mViewModel.mSendMsg.observe(this) {
            ToastUtils.showShort(getString(R.string.send_suc))
            mCountDownTimer.start()
        }
        mViewModel.mUser.observe(this) {
            ToastUtils.showShort(R.string.login_suc)
            UserInfoUtils.setUser(it)
            LiveEventBus.get(LiveEventBusKey.LOGIN_SUC)
                .post(it)
            finish()
        }
        mViewModel.sendCode(mPhone)
    }

    private val mCountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            mBinding.tvTips.apply {
                text = context.getString(R.string.resend_code)
                setTextColor(ContextCompat.getColor(mContext, R.color.main_color))
                isClickable = true
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            mBinding.tvTips.apply {
                text = String.format(
                    context.getString(R.string.resend_code_able),
                    millisUntilFinished / 1000
                )
                setTextColor(ContextCompat.getColor(mContext, R.color.text_gray))
                isClickable = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }
}
