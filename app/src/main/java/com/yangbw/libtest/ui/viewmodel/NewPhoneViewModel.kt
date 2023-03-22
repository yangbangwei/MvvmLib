package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class NewPhoneViewModel : BaseViewModel<ApiService>() {

    val mSendCode = MutableLiveData<String>()
    val mChangePhone = MutableLiveData<String>()

    fun sendCode(phone: String) {
        launchOnlyResult(
            block = {
                getApiService().sendCode(phone)
            },
            success = {
                mSendCode.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }

    fun changePhone(phone: String, code: String) {
        launchOnlyResult(
            block = {
                getApiService().changePhone(phone, code)
            },
            success = {
                mChangePhone.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }
}
