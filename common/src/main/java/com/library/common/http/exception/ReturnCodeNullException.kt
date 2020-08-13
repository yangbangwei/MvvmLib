package com.library.common.http.exception

/**
 * @author yangbw
 * @date 2020/4/18.
 * module：
 * description：多baseurl时，未设置对应的成功returnCode
 */
class ReturnCodeNullException
/**
 * 未设置对应的成功returnCode提示
 *
 * @param returnCode
 * @param msg
 */(val returnCode: String?, msg: String?) :
    Exception(msg)