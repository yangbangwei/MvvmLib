package com.yangbw.libtest.module.login

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.App
import com.yangbw.libtest.R
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.module.login.UserData

/**
 * @author yangbw
 * @date
 */
class CodeViewModel : BaseViewModel<ApiService>() {

    val mSendMsg = MutableLiveData<String>()
    val mUser = MutableLiveData<UserData>()

    public override fun onStart() {

    }

    fun sendCode(phone: String) {
        launchOnlyResult(
            block = {
                getApiService().sendCode(phone)
            },
            success = {
                mSendMsg.value = it
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
                mUser.value = it
            },
            type = RequestDisplay.TOAST,
            msg = App.context.getString(R.string.logining)
        )
    }
}
