package com.yangbw.libtest.module.home

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService


/**
 * 首页viewModel
 *
 * @author :yangbw
 * @date :2020/9/7
 */
class HomeViewModel : BaseViewModel<ApiService>() {

    val mBanners = MutableLiveData<List<BannerInfo>>()
    val mVersion = MutableLiveData<UpdateVersion>()

    public override fun onStart() {
        getBanner()
    }

    fun updateVersion(versionCode: Int) {
        launchOnlyResult(
            block = {
                getApiService().updateVersion(versionCode)
            },
            success = {
                mVersion.value = it
            }
        )
    }

    private fun getBanner() {
        viewState.showLoading.call()
        launchOnlyResult(
            block = {
                getApiService().banners()
            },
            success = {
                mBanners.value = it
            },
            reTry = {
                onStart()
            },
            type = RequestDisplay.REPLACE
        )
    }
}
