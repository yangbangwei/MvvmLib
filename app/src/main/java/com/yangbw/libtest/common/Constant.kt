package com.yangbw.libtest.common

/**
 * 全局配置
 *
 * @author yangbw
 * @date 2020/9/1
 */
object Constant {
    //基本配置
    const val OPEN_LOG: Boolean = true

    const val BASE_URL: String = "http://192.168.1.234:9999/"

    //接口成功标识符
    const val CODE_SUCCESS: String = "200"

    //倒计时相关
    const val TIME_CODE: Long = 60000
    const val TIME_START: Long = 3000
    const val SECOND: Long = 1000
}