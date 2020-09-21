package com.yangbw.libtest.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
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

        /**
         * 设置协议字体
         */
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

        /**
         * 如果输入法在窗口上已经显示，则隐藏，反之则显示
         */
        fun showOrHide(context: Context) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        /**
         * view为接受软键盘输入的视图，SHOW_FORCED表示强制显示
         */
        fun showOrHide(context: Context, view: View) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //  imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);//SHOW_FORCED表示强制显示
            imm.hideSoftInputFromWindow(view.windowToken, 0) //强制隐藏键盘
        }

        /**
         * 调用隐藏系统默认的输入法
         */
        fun showOrHide(activity: Activity) {
            (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
        }

        /**
         * 获取输入法打开的状态
         */
        fun isShowing(context: Context): Boolean {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return imm.isActive() //isOpen若返回true，则表示输入法打开
        }
    }
}