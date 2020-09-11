package com.yangbw.libtest.module.discover.hot

import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseListViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class HotViewModel : BaseListViewModel<ApiService>() {

    public override fun onStart() {

    }

    fun discoverHot(pageNo: Int) {
        launchOnlyResult(
            block = {
                getApiService().discoverHot(pageNo)
            },
            reTry = {
                discoverHot(pageNo)
            },
            pageNo = pageNo
        )
    }
}
