package com.yangbw.libtest.module.coupon

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class CouponData(
    val deadline: String,
    val derate: String,
    val price: String,
    val title: String
)
