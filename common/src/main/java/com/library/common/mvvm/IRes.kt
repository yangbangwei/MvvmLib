package com.library.common.mvvm

/**
 * @author yangbw
 * @date 2020/3/13.
 * module：
 * description：
 */
interface IRes<T> {
    fun getBaseMsg(): String
    fun getBaseCode(): String
    fun getBaseResult(): T
}