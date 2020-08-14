package com.qianxinde.libtest.module.mine;

import androidx.annotation.Keep
import com.qianxinde.libtest.module.home.BannerInfo

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
    val blance: String? = null,
    val banners: List<BannerInfo>? = null
)
