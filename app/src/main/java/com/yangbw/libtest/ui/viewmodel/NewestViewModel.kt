package com.yangbw.libtest.ui.viewmodel

import com.library.common.mvvm.BaseListViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author :yangbw
 * @date :2020/9/9
 */
class NewestViewModel : BaseListViewModel<ApiService>() {

    fun discoverNew(pageNo: Int) {
        launchOnlyResult(
            block = {
                getApiService().discoverNew(pageNo)
            },
            reTry = {
                discoverNew(pageNo)
            },
            pageNo = pageNo
        )
    }

}
