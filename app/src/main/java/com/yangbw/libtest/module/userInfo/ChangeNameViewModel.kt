package com.yangbw.libtest.module.userInfo

import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class ChangeNameViewModel : BaseViewModel<ApiService>() {

    public override fun onStart() {

    }

    fun changeName(name: String) {
        launchOnlyResult(
            block = {
                getApiService().changeName(name)
            },
            type = RequestDisplay.TOAST
        )
    }
}
