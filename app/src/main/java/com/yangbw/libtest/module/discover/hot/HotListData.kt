package com.yangbw.libtest.module.discover.hot

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
