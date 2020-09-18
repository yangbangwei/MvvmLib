package com.yangbw.libtest.module.login

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.App
import com.yangbw.libtest.R
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class LoginViewModel : BaseViewModel<ApiService>() {

    var mUser = MutableLiveData<UserData>()

    public override fun onStart() {

    }

    fun login(phone: String) {
        launchOnlyResult(
            block = {
                getApiService().loginByPhone(phone)
            },
            success = {
                mUser.value = it
            },
            type = RequestDisplay.TOAST,
            msg = App.context.getString(R.string.logining)
        )
    }
}
