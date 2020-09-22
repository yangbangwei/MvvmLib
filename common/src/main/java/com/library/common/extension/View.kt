@file:Suppress("unused")

package com.library.common.extension

import android.text.TextUtils
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.EditText
import android.widget.TextView

/**
 * 显示view
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * 显示view，带有渐显动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.visibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.VISIBLE
    this?.startAnimation(AlphaAnimation(0f, 1f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 隐藏view
 */
fun View?.gone() {
    this?.visibility = View.GONE
}

/**
 * 隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.goneAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.GONE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 占位隐藏view
 */
fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

/**
 * 占位隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.invisibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.INVISIBLE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 获取EditText的值
 */
fun EditText?.getTextToString(): String {
    return this?.text.toString()
}

/**
 * 获取TextView的值
 */
fun TextView?.getTextToString(): String {
    return this?.text.toString()
}

/**
 * 获取输入框的值
 */
fun EditText?.isEmpty(): Boolean {
    return TextUtils.isEmpty(this?.text.toString())
}

/**
 * 获取输入框的值
 */
fun TextView?.isEmpty(): Boolean {
    return TextUtils.isEmpty(this?.text.toString())
}

/**
 * 将光标移动到末尾
 */
fun EditText?.setSelectionToEnd() {
    this?.requestFocus()
    this?.setSelection(this.text.toString().length)
}