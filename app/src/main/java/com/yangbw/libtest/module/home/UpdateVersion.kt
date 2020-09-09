package com.yangbw.libtest.module.home

/**
 * @author :yangbw
 * @date :2020/8/7
 */
data class UpdateVersion(
    val content: String,
    val isHas: Boolean,
    val url: String,
    val version: String
)
