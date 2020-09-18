package com.yangbw.libtest.dialog.coupon

import androidx.lifecycle.MutableLiveData
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class CouponViewModel : BaseViewModel<ApiService>() {

    var mData = MutableLiveData<List<CouponData>>()

    public override fun onStart() {
        launchOnlyResult(
            block = {
                getApiService().couponList()
            },
            success = {
                mData.value = it
            }
        )
    }
}
