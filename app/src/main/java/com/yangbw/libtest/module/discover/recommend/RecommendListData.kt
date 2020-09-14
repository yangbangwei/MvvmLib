package com.yangbw.libtest.module.discover.recommend

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class RecommendListData(
    val id: String,
    val img: String,
    val title: String,
    val avatar: String,
    val name: String,
    val date: String,
    val like: String,
    val width: Int,
    val height: Int
)
