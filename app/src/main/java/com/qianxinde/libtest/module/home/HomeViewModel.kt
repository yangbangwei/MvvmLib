package com.qianxinde.libtest.module.home;

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseListViewModel
import com.qianxinde.libtest.api.ApiService


/**
 * @author yangbw
 * @date
 * module：
 * description：
 */
class HomeViewModel : BaseListViewModel<ApiService>() {

    val mBanners = MutableLiveData<List<BannerInfo>>()
    val mVersion = MutableLiveData<UpdateVersion>()

    public override fun onStart() {
        getBanner()
        getHomes(1)
    }

    fun getBanner(){
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
            type = RequestDisplay.NULL,
            isList = false
        )
    }

    fun updateVersion(versionCode: Int) {
        launchOnlyResult(
            block = {
                getApiService().updateVersion(versionCode)
            },
            success = {
                mVersion.value = it
            },
            type = RequestDisplay.NULL,
            isList = false
        )
    }

    fun getHomes(pageNo: Int) {
        launchOnlyResult(
            block = {
                getApiService().homes(pageNo)
            },
            success = {

            },
            reTry = {
                onStart()
            },
            type = RequestDisplay.REPLACE,
            pageNo = pageNo
        )
    }

}
