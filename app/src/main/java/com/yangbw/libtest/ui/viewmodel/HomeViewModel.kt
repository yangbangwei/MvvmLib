package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.entity.BannerInfo
import com.yangbw.libtest.entity.UpdateVersion


/**
 * 首页viewModel
 *
 * @author :yangbw
 * @date :2020/9/7
 */
class HomeViewModel : BaseViewModel<ApiService>() {

    val mBanners = MutableLiveData<List<BannerInfo>>()
    val mVersion = MutableLiveData<UpdateVersion>()

    @Suppress("unused")
    fun updateVersion(versionCode: Int) {
        launchOnlyResult(
            block = {
                getApiService().updateVersion(versionCode)
            },
            success = {
                mVersion.value = it.getBaseResult()
            },
            type = RequestDisplay.NULL
        )
    }

    fun getBanner() {
        launchOnlyResult(
            block = {
                getApiService().banners()
            },
            success = {
                mBanners.value = it.getBaseResult()
            },
            reTry = {
                getBanner()
            }
        )
    }
}
