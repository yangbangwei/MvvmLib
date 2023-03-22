package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.entity.DetailsData

/**
 * @author yangbw
 * @date
 */
class DetailsViewModel : BaseViewModel<ApiService>() {

    var mDetails = MutableLiveData<DetailsData>()

    fun getHomeDetails(id: String) {
        launchOnlyResult(
            block = {
                getApiService().homeDetails(id)
            },
            reTry = {
                getHomeDetails(id)
            },
            success = {
                mDetails.value = it.getBaseResult()
            }
        )
    }
}
