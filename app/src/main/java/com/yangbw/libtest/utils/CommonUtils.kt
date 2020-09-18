package com.yangbw.libtest.utils

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yangbw.libtest.R
import com.yangbw.libtest.module.common.WebActivity

/**
 * 常用的工具类
 *
 * @author :yangbw
 * @date :2020/8/12
 */
class CommonUtils {
    companion object {

        fun setAgreementTxt(
            context: Context,
            tvAgreement: TextView,
            colorResId: Int = R.color.blue,
            isUnderLine: Boolean = false
        ) {
            //协议样式布局
            val builder = SpannableStringBuilder(tvAgreement.text)
            //单独设置字体颜色
            builder.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, colorResId)),
                8, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, colorResId)),
                19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, colorResId)),
                26, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            //单独设置点击事件
            builder.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.launch(
                        context,
                        null,
                        "https://admin.qidian.qq.com/template/blue/website/agreement.html"
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = isUnderLine
                }
            }, 8, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.launch(
                        context,
                        null,
                        "https://admin.qidian.qq.com/template/blue/website/agreement.html"
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = isUnderLine
                }
            }, 19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.launch(
                        context,
                        null,
                        "https://admin.qidian.qq.com/template/blue/website/agreement.html"
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = isUnderLine
                }
            }, 26, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //去掉点击后文字的背景色 (不去掉回有默认背景色)
            tvAgreement.highlightColor = Color.parseColor("#00000000")
            //不设置不生效
            tvAgreement.movementMethod = LinkMovementMethod.getInstance()
            tvAgreement.text = builder
        }
    }
}