package com.library.common.http.interceptor

/**
 * 返回Code拦截处理
 *
 * @author yangbw
 * @date 2020/8/31
 */
interface IReturnCodeErrorInterceptor {
    /**
     * 根据返回的returnCode，判断是否进行拦截
     *
     * @param returnCode
     * @return
     */
    fun intercept(returnCode: String?): Boolean

    /**
     * intercept(String returnCode)方法为true的时候调用该方法
     *
     * @param returnCode
     * @param msg
     */
    fun doWork(returnCode: String?, msg: String?)
}