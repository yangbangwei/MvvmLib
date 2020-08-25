package com.yangbw.libtest.module.details

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class DetailsData(
    val content: String,
    val id: String,
    val img: String,
    val title: String
)
