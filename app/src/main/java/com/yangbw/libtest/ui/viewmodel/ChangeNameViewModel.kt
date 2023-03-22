package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class ChangeNameViewModel : BaseViewModel<ApiService>() {

    val mChangeName = MutableLiveData<String>()

    fun changeName(name: String) {
        launchOnlyResult(
            block = {
                getApiService().changeName(name)
            },
            success = {
                mChangeName.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }
}
