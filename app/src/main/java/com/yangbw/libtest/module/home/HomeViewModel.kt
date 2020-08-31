package com.yangbw.libtest.module.home;

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseListViewModel
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn


/**
 * @author yangbw
 * @date
 * module：
 * description：
 */
class HomeViewModel : BaseViewModel<ApiService>() {

    val mBanners = MutableLiveData<List<BannerInfo>>()
    val mVersion = MutableLiveData<UpdateVersion>()

    public override fun onStart() {
        getBanner()
        getHomes(1)
    }

    fun updateVersion(versionCode: Int) {
        launchOnlyResult(
            block = {
                getApiService().updateVersion(versionCode)
            },
            success = {
                mVersion.value = it
            },
            reTry = {
                updateVersion(versionCode)
            },
            type = RequestDisplay.NULL
        )
    }

    private fun getBanner(){
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
            type = RequestDisplay.NULL
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
            type = RequestDisplay.REPLACE
        )
    }

}
