package com.qianxinde.libtest.module.mine;

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.qianxinde.libtest.api.ApiService


/**
 * @author yangbw
 * @date
 * module：
 * description：
 */
class MineViewModel : BaseViewModel<ApiService>() {

    var mUserInfo = MutableLiveData<MineData>()

    public override fun onStart() {

    }

    fun getUserInfo() {
        launchOnlyResult(
            block = {
                getApiService().userInfo()
            },
            success = {
                mUserInfo.value = it
            },
            reTry = {
                getUserInfo()
            },
            type = RequestDisplay.REPLACE
        )
    }

}
