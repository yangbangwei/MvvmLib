package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class ChangePhoneViewModel : BaseViewModel<ApiService>() {

    val mSendCode = MutableLiveData<String>()
    val mVerifyCode = MutableLiveData<String>()

    fun sendCode() {
        launchOnlyResult(
            block = {
                getApiService().sendCode()
            },
            success = {
                mSendCode.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }

    fun verifyCode(code: String) {
        launchOnlyResult(
            block = {
                getApiService().verifyCode(code)
            },
            success = {
                mVerifyCode.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }
}
