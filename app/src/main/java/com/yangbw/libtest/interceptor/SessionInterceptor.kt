package com.yangbw.libtest.interceptor

import com.android.aachulk.consts.Constant
import com.android.aachulk.consts.LiveEventBusKey
import com.blankj.utilcode.util.SPUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.http.interceptor.IReturnCodeErrorInterceptor

/**
 * @author yangbw
 * @date 2019/3/4.
 * module：
 * description：returnCode返回session_100 拦截处理
 */
class SessionInterceptor : IReturnCodeErrorInterceptor {
    //和接口定义互踢的相关参数返回，然后在doWork方法进行跳转
    override fun intercept(returnCode: String?): Boolean {
        return "-100" == returnCode
    }

    override fun doWork(returnCode: String?, msg: String?) {
        SPUtils.getInstance().remove(Constant.TOKEN)
        //传递参数
        LiveEventBus
            .get(LiveEventBusKey.LOGIN_OUT)
            .post("")
    }

}