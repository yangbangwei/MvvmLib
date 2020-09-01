package com.library.common.http.exception

/**
 * 未设置Code异常
 *
 * @author yangbw
 * @date 2020/4/18.
 */
class ReturnCodeNullException
/**
 * 未设置对应的成功returnCode提示
 *
 * @param returnCode
 * @param msg
 */(private val returnCode: String?, msg: String?) :
    Exception(msg)