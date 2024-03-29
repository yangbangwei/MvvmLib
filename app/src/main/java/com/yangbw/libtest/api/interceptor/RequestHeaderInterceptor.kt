package com.android.aachulk.interceptor

import com.library.common.http.interceptor.BaseUrlInterceptor
import com.yangbw.libtest.utils.UserInfoUtils
import okhttp3.Headers

/**
 * Token统一封装
 *
 * @author yangbw
 * @date 2020/9/1
 */
class RequestHeaderInterceptor : BaseUrlInterceptor() {

    override fun addHeaders(): Headers {
        return Headers.Builder()
            .add("token", UserInfoUtils.getToken())
            .build()
    }
}