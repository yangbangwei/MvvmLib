package com.library.common.mvvm

/**
 * 通用实体
 *
 * @author yangbw
 * @date 2020/8/31
 */
interface IRes<T> {
    fun getBaseMsg(): String
    fun getBaseCode(): String
    fun getBaseResult(): T
}