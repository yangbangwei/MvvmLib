package com.android.aachulk.interceptor

import com.android.aachulk.consts.Constant
import com.blankj.utilcode.util.SPUtils
import com.library.common.http.interceptor.BaseUrlInterceptor
import okhttp3.Headers

/**
 * @author yangbw
 * @date 2019-07-06.
 * module：
 * description：请求头拦截器,如果需要设置多baseurl得继承BaseUrlInterceptor去写
 *                                   单baseurl直接继承Interceptor去写
 */
class RequestHeaderInterceptor : BaseUrlInterceptor() {

    override fun addHeaders(): Headers {
        return Headers.Builder()
            .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
            .build()
    }


//    //单baseurl的写法参考 统一请求头的封装根据自身项目添加
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val authorised: Request
//        val headers = Headers.Builder()
//            .add("app_id", "wpkxpsejggapivjf")
//            .add("app_secret", "R3FzaHhSSXh4L2tqRzcxWFBmKzBvZz09")
//            .build()
//        authorised = request.newBuilder().headers(headers).build()
//        return chain.proceed(authorised)
//    }
}