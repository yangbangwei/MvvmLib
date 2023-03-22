package com.yangbw.libtest.common

import androidx.annotation.Keep
import com.library.common.mvvm.IRes

/**
 * @author yangbw
 * @date 2020-03-16.
 * module：接口返回封装类
 * description：
 */
@Keep
data class BaseRes<T>(
    val msg: String, val code: String, val `data`: T
) : IRes<T> {
    override fun getBaseMsg() = msg
    override fun getBaseCode() = code
    override fun getBaseResult() = data
}