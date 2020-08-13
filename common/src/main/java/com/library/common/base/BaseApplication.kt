package com.library.common.base

import android.app.Application
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author yangbw
 * @date 2020/3/16.
 * module：
 * description：
 */
abstract class BaseApplication : Application() {
    abstract fun initConfig() //初始化配置参数
    override fun onCreate() {
        super.onCreate()
        initConfig()
        //ARouter的初始化
        ARouter.init(this)
        //分包
        MultiDex.install(this);
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

}