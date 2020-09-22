package com.yangbw.libtest.module.userInfo

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class ChangePhoneViewModel : BaseViewModel<ApiService>() {

    val mSendCode = MutableLiveData<Boolean>()
    val mVerifyCode = MutableLiveData<Boolean>()

    public override fun onStart() {

    }

    fun sendCode() {
        launchOnlyResult(
            block = {
                getApiService().sendCode()
            },
            success = {
                mSendCode.value = true
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
                mVerifyCode.value = true
            },
            type = RequestDisplay.TOAST
        )
    }
}
