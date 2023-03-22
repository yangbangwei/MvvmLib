package com.library.common.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.tencent.mmkv.MMKV

/**
 * BaseApplication封装
 *
 * @author yangbw
 * @date 2020/9/1
 */
abstract class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    abstract fun initConfig() //初始化配置参数

    override fun onCreate() {
        super.onCreate()
        context = this
        MMKV.initialize(this)
        MultiDex.install(this)

        initConfig()
    }

}