package com.yangbw.libtest.module.discover.recommend

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseListViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class RecommendViewModel : BaseListViewModel<ApiService>() {

    val mLike = MutableLiveData<String>()

    fun discoverRecommend(pageNo: Int) {
        launchOnlyResult(
            block = {
                getApiService().discoverRecommend(pageNo)
            }, reTry = {
                discoverRecommend(pageNo)
            },
            pageNo = pageNo
        )
    }

    fun discoverRecommendLike(id: String) {
        launchOnlyResult(
            block = {
                getApiService().discoverRecommendLike(id)
            },
            success = {
                mLike.value = it.getBaseMsg()
            },
            type = RequestDisplay.TOAST
        )
    }
}
