package com.library.common.http.exception

/**
 * @author yangbw
 * @date 2020/8/31
 */
class NetWorkException : Exception {
    /**
     * 网络错误
     */
    constructor() : super() {}

    /**
     * 网络错误传入错误内容
     * @param msg
     */
    constructor(msg: String?) : super(msg) {}
}