package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.entity.MineData


/**
 * 我的页面ViewModel
 *
 * @author :yangbw
 * @date :2020/9/8
 */
class MineViewModel : BaseViewModel<ApiService>() {

    var mUserInfo = MutableLiveData<MineData>()

    override fun onStart() {
        launchOnlyResult(
            block = {
                getApiService().userInfo()
            },
            success = {
                mUserInfo.value = it.getBaseResult()
            },
            reTry = {
                onStart()
            }
        )
    }
}
