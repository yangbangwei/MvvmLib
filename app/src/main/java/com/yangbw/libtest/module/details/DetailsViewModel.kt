package com.yangbw.libtest.module.details

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class DetailsViewModel : BaseViewModel<ApiService>() {

    var mDetails = MutableLiveData<DetailsData>()

    public override fun onStart() {

    }

    fun getHomeDetails(id: String) {
        launchOnlyResult(
            block = {
                getApiService().homeDetails(id)
            },
            reTry = {
                getHomeDetails(id)
            },
            success = {
                mDetails.value = it
            },
            type = RequestDisplay.REPLACE
        )
    }
}
