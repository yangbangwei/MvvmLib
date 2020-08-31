package com.yangbw.libtest

import com.android.aachulk.consts.Constant
import com.android.aachulk.interceptor.RequestHeaderInterceptor
import com.library.common.base.BaseApplication
import com.library.common.config.AppConfig
import com.library.common.http.ApiClient
import com.library.common.utils.Utils
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yangbw.libtest.interceptor.SessionInterceptor
import com.yangbw.libtest.view.NoStatusFooter
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author yangbw
 * @date 2020/3/16
 */
class App : BaseApplication() {

    init {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(false)
            MaterialHeader(context).setColorSchemeResources(R.color.main_color, R.color.main_color,
                R.color.main_color)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)
            layout.setEnableFooterTranslationContent(true)
            layout.setFooterHeight(153f)
            layout.setFooterTriggerRate(0.6f)
            NoStatusFooter.REFRESH_FOOTER_NOTHING = "- 我是有底线的人 -"
            NoStatusFooter(context).apply {
                setAccentColorId(R.color.colorTextPrimary)
                setTextTitleSize(16f)
            }
        }
    }

    override fun initConfig() {
        AppConfig.builder()
            .setRetSuccess(Constant.CODE_SUCCESS)
            .setBaseUrl(Constant.BASE_URL)
            //日志开关
            .setLogOpen(Constant.OPEN_LOG)
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
    }
}