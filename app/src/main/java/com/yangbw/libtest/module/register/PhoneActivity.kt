package com.yangbw.libtest.module.register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.RegexUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.extension.invisible
import com.library.common.extension.setOnClickListener
import com.library.common.extension.visible
import com.yangbw.libtest.R
import com.yangbw.libtest.common.CommonViewModel
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.databinding.ActivityPhoneBinding
import com.yangbw.libtest.module.common.WebActivity
import com.yangbw.libtest.module.login.UserData
import kotlinx.android.synthetic.main.activity_phone.*
import android.text.TextWatcher as TextWatcher1

/**
 * 输入手机号
 *
 * @author :yangbw
 * @date :2020/9/18
 */
class PhoneActivity : BaseActivity<CommonViewModel, ActivityPhoneBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, PhoneActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_phone

    override fun getReplaceView(): View = activity_phone

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            statusBarColor(com.library.common.R.color.white)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.run {
            //点击事件
            setOnClickListener(ivClose, ivService, btnSend, ivWchat) {
                when (this) {
                    ivClose -> finish()
                    ivService -> showToast(context.getString(R.string.service_tips))
                    btnSend -> {
                        CodeActivity.launch(mContext, etPhone.text.toString())
                    }
                    ivWchat -> showToast(context.getString(R.string.wchat_login_tips))
                }
            }
            //界面初始化
            val builder = SpannableStringBuilder(tvAgreement.text)
            //单独设置字体颜色
            builder.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.blue)),
                4, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.blue)),
                12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            //单独设置点击事件
            builder.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.launch(
                        mContext,
                        null,
                        "https://admin.qidian.qq.com/template/blue/website/agreement.html"
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
            }, 5, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.launch(
                        mContext,
                        null,
                        "https://admin.qidian.qq.com/template/blue/website/agreement.html"
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
            }, 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //去掉点击后文字的背景色 (不去掉回有默认背景色)
            tvAgreement.highlightColor = Color.parseColor("#00000000")
            //不设置不生效
            tvAgreement.movementMethod = LinkMovementMethod.getInstance()
            tvAgreement.text = builder
            //监听账号输入
            etPhone.addTextChangedListener(object : TextWatcher1 {
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
                    if (s.toString().length == 11) {
                        if (RegexUtils.isMobileExact(s.toString())) {
                            btnSend.isEnabled = true
                            tvTips.invisible()
                        } else {
                            btnSend.isEnabled = false
                            tvTips.visible()
                        }
                    } else {
                        tvTips.invisible()
                        btnSend.isEnabled = false
                    }
                }
            })

        }

        //短信验证码登录成功，关闭该页面
        LiveEventBus.get(LiveEventBusKey.LOGIN_SUC, UserData::class.java)
            .observe(this, {
                finish()
            })
    }

}
