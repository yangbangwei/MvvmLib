package com.yangbw.libtest.module.mine

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.module.home.BannerInfo


/**
 * 我的页面ViewModel
 *
 * @author :yangbw
 * @date :2020/9/8
 */
class MineViewModel : BaseViewModel<ApiService>() {

    var mUserInfo = MutableLiveData<MineData>()

    public override fun onStart() {
        getUserInfo()
    }

    private fun getUserInfo() {
        launchOnlyResult(
            block = {
                getApiService().userInfo()
            },
            success = {
                mUserInfo.value = it
            },
            reTry = {
                getUserInfo()
            }
        )
    }


}
