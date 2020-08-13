package com.qianxinde.libtest

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.android.aachulk.consts.Constant
import com.android.aachulk.interceptor.RequestHeaderInterceptor
import com.qianxinde.libtest.interceptor.SessionInterceptor
import com.library.common.base.BaseApplication
import com.library.common.config.AppConfig
import com.library.common.http.ApiClient
import com.library.common.utils.Utils
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author yangbw
 * @date 2020/3/16.
 * module：
 * description：
 */
class App : BaseApplication() {

    override fun initConfig() { //配置项
        AppConfig.builder() //这里只需要选择设置一个
            .setRetSuccess(Constant.CODE_SUCCESS)
//            .setRetSuccessList(Constant.CODELIST_SUCCESS)
            //设置多baseUrl的retCode
//            .addRetSuccess(Constant.WANANDROID_DOMAIN_NAME, Constant.WANANDROID_CODELIST_SUCCESS)
//            .addRetSuccess(Constant.GANK_DOMAIN_NAME, Constant.GANK_CODELIST_SUCCESS)
            .setBaseUrl(Constant.BASE_URL)
            //设置多baseUrl
//            .addDomain(Constant.WANANDROID_DOMAIN_NAME, Constant.WANANDROID_API)
//            .addDomain(Constant.GANK_DOMAIN_NAME, Constant.GANK_API)
            //日志开关
            .setLogOpen(Constant.OPEN_LOG)
            //路由开关
            .setARouterOpen(Constant.OPEN_AROUTER)
            //请求头拦截器
            .addOkHttpInterceptor(RequestHeaderInterceptor())
            //请求日志开关
            .addOkHttpInterceptor(
                Constant.OPEN_LOG,
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            // returnCode非正常态拦截器
            .addRetCodeInterceptors(SessionInterceptor())
            .setRetrofit(
                ApiClient.getInstance().getRetrofit(
                    ApiClient.getInstance().getOkHttpClient(
                        AppConfig.getOkHttpInterceptors()
                    )
                )
            )
            .build()
        //application 上下文
        Utils.init(this)
        //ARouter的初始化
        initArouter()
        //SmartRefreshLayout的统一设置
        initSmartRefreshLayout()
    }

    /**
     * ARouter的初始化
     */
    private fun initArouter() {
        if (Constant.OPEN_LOG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    /**
     * SmartRefreshLayout的统一设置
     */
    private fun initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, _: RefreshLayout ->
            return@setDefaultRefreshHeaderCreator MaterialHeader(context)
        }

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, _: RefreshLayout ->
            return@setDefaultRefreshFooterCreator ClassicsFooter(context).setDrawableSize(20f)
        }
    }

}