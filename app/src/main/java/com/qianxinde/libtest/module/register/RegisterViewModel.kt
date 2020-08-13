package com.qianxinde.libtest.module.register

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.qianxinde.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class RegisterViewModel : BaseViewModel<ApiService>() {

    var mResult = MutableLiveData<String>()

    public override fun onStart() {

    }

    fun register(username: String, password: String) {
        launchOnlyResult(
            block = {
                getApiService().register(username, password)
            },
            success = {
                mResult.value = it
            },
            type = RequestDisplay.TOAST,
            msg = "正在注册..."
        )
    }

}
