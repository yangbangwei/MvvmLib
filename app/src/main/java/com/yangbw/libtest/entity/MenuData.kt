package com.yangbw.libtest.entity

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class MenuData(
    val attention: String,
    val avatar: String,
    val content: String,
    val id: String,
    val like: String,
    val name: String,
    val picUrls: List<String>,
    val type: String,
    val videoUrl: String
)