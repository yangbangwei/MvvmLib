package com.library.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

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
        //分包
        MultiDex.install(this);
    }

    companion object {
        lateinit var context: Context
    }

}