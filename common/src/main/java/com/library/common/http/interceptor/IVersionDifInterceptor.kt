package com.library.common.http.interceptor

/**
 * 版本异常处理
 *
 * @author yangbw
 * @date 2020/8/31
 */
@Suppress("unused")
interface IVersionDifInterceptor {
    /**
     * 根据返回的version，判断是否进行拦截
     *
     * @param version
     * @return
     */
    fun intercept(version: String?): Boolean

    /**
     * intercept(String returnCode)方法为true的时候调用该方法
     *
     * @param serviceVersion
     */
    fun doWork(serviceVersion: String?)
}