package com.library.common.http.exception

/**
 * @author yangbw
 * @date 2020/8/31
 */
class ResultException : Exception {
    /**
     * 数据返回异常
     */
    constructor() : super() {}

    /**
     * 数据返回异常传入错误内容
     * @param msg
     */
    constructor(msg: String?) : super(msg) {}
}