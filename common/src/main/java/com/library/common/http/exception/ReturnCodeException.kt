package com.library.common.http.exception

/**
 * @author yangbw
 * @date 2020/8/31
 */
class ReturnCodeException
/**
 * 响应码异常，返回异常的响应码及其显示错误的提示
 *
 * @param returnCode
 * @param msg
 */(val returnCode: String, msg: String?) :
    Exception(msg)