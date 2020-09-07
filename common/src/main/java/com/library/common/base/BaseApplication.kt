package com.library.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * BaseApplication封装
 *
 * @author yangbw
 * @date 2020/9/1
 */
abstract class BaseApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    abstract fun initConfig() //初始化配置参数

    override fun onCreate() {
        super.onCreate()
        context = this
        initConfig()
        //分包
        MultiDex.install(this)
    }

}