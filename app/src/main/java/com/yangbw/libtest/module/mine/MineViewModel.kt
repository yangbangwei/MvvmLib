package com.yangbw.libtest.module.mine

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.module.home.BannerInfo
import utils.UserInfoUtils


/**
 * 我的页面ViewModel
 *
 * @author :yangbw
 * @date :2020/9/8
 */
class MineViewModel : BaseViewModel<ApiService>() {

    var mUserInfo = MutableLiveData<MineData>()
    var mBanner = MutableLiveData<List<BannerInfo.Data>>()

    public override fun onStart() {
        getUserBanner()
        getUserInfo()
    }

    private fun getUserBanner() {
        launchOnlyResult(
            block = {
                getApiService().userBanner()
            },
            success = {
                mBanner.value = it
            }
        )
    }

    private fun getUserInfo() {
        if (UserInfoUtils.isLogin()) {
            launchOnlyResult(
                block = {
                    getApiService().userInfo()
                },
                success = {
                    mUserInfo.value = it
                }
            )
        }
    }


}
