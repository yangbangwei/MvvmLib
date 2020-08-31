package com.library.common.mvvm

/**
 * @author yangbw
 * @date 2020/8/31
 * module：
 * description：
 */
interface IRes<T> {
    fun getBaseMsg(): String
    fun getBaseCode(): String
    fun getBaseResult(): T
}