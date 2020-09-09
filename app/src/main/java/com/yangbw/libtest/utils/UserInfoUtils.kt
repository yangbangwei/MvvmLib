package com.yangbw.libtest.utils

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.yangbw.libtest.module.userInfo.UserInfoData

/**
 * 用户信息管理类
 *
 * @author yangbw
 * @date 2020/9/8
 */
@Suppress("unused")
class UserInfoUtils {

    companion object {
        private const val USER_INFO = "user_info"

        fun setUser(userInfo: UserInfoData) {
            SPUtils.getInstance().put(USER_INFO, GsonUtils.toJson(userInfo))
        }

        fun getUser(): UserInfoData? {
            val userInfo = SPUtils.getInstance().getString(USER_INFO)
            if (TextUtils.isEmpty(userInfo)) {
                return null
            }
            return GsonUtils.fromJson(userInfo, UserInfoData::class.java)
        }

        fun isLogin(): Boolean {
            return getUser() != null
        }

        fun logout() {
            SPUtils.getInstance().remove(USER_INFO)
        }
    }
}