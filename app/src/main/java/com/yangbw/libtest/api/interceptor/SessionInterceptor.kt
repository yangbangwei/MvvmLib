package com.yangbw.libtest.api.interceptor

import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.http.interceptor.IReturnCodeErrorInterceptor
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.utils.UserInfoUtils

/**
 * 登录失效拦截
 *
 * @author yangbw
 * @date 2019/3/4.
 */
class SessionInterceptor : IReturnCodeErrorInterceptor {
    //和接口定义互踢的相关参数返回，然后在doWork方法进行跳转
    override fun intercept(returnCode: String?): Boolean {
        return "-100" == returnCode
    }

    override fun doWork(returnCode: String?, msg: String?) {
        UserInfoUtils.logout()
        //传递参数
        LiveEventBus
            .get(LiveEventBusKey.LOGIN_OUT)
            .post("")
    }

}