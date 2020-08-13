package com.qianxinde.libtest.module.login

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class UserData constructor(
    val id: Long?,
    val password: String?,
    val username: String?,
    val token: String?
) {
    constructor(password: String, username: String) : this(null, password, username, null)
}

