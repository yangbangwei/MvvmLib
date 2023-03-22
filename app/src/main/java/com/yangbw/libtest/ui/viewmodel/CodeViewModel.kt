package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.App
import com.yangbw.libtest.R
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.entity.UserData

/**
 * @author yangbw
 * @date
 */
class CodeViewModel : BaseViewModel<ApiService>() {

    val mSendMsg = MutableLiveData<String>()
    val mUser = MutableLiveData<UserData>()

    fun sendCode(phone: String) {
        launchOnlyResult(
            block = {
                getApiService().sendCode(phone)
            },
            success = {
                mSendMsg.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }

    fun login(phone: String, code: String) {
        launchOnlyResult(
            block = {
                getApiService().login(phone, code)
            },
            success = {
                mUser.value = it.getBaseResult()
            },
            type = RequestDisplay.TOAST,
            msg = App.context.getString(R.string.logining)
        )
    }
}
