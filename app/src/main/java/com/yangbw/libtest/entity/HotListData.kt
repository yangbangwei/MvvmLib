package com.yangbw.libtest.entity

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class HotListData(
    val id: String,
    val img: String,
    val title: String,
    val avatar: String,
    val name: String,
    val date: String
)
