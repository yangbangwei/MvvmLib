package com.yangbw.libtest.entity

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 * module：
 * description：
 */
@Keep
data class MineData(
    val name: String? = null,
    val head: String? = null,
    val present: String? = null,
    val discount: String? = null,
    val balance: String? = null,
    val banners: List<BannerInfo.Data>? =null
)
