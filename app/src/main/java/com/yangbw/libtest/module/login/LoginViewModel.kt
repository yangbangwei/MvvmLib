package com.yangbw.libtest.module.login

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class LoginViewModel : BaseViewModel<ApiService>() {

    var mUser = MutableLiveData<UserData>()

    public override fun onStart() {

    }

    fun login(username: String, password: String) {
        launchOnlyResult(
            block = {
                getApiService().login(username, password)
            },
            success = {
                mUser.value = it
            },
            type = RequestDisplay.TOAST,
            msg = "登录中"
        )
    }
}
