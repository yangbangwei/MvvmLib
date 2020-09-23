package com.yangbw.libtest.dialog.coupon

import androidx.annotation.Keep

/**
 * @author yangbw
 */
@Keep
data class CouponData(
    val deadline: String,
    val derate: String,
    val price: String,
    val title: String
)
