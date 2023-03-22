package com.yangbw.libtest.ui.viewmodel

import com.library.common.mvvm.BaseListViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class HotViewModel : BaseListViewModel<ApiService>() {

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
