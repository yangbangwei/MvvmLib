package com.yangbw.libtest.entity

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class UserData constructor(
    val id: Long,
    val password: String,
    val username: String,
    val phone: String?,
    val wechat: String?,
    val avatar: String?,
    val token: String
)

