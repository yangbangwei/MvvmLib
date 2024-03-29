package com.yangbw.libtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService
import com.yangbw.libtest.entity.CouponData

/**
 * @author yangbw
 * @date
 */
class CouponViewModel : BaseViewModel<ApiService>() {

    var mData = MutableLiveData<List<CouponData>>()

    override fun onStart() {
        launchOnlyResult(
            block = {
                getApiService().couponList()
            },
            success = {
                mData.value = it.getBaseResult()
            }
        )
    }
}
