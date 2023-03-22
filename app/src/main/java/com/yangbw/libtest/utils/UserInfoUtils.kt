package com.yangbw.libtest.utils

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import com.library.common.utils.MMKvUtils
import com.yangbw.libtest.entity.UserData

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

        /**
         * 登录成功，缓存用户信息
         */
        fun setUser(userInfo: UserData) {
            MMKvUtils.put(USER_INFO, GsonUtils.toJson(userInfo))
        }

        /**
         * 获取用户信息
         */
        fun getUser(): UserData? {
            val userInfo = MMKvUtils.getString(USER_INFO)
            if (TextUtils.isEmpty(userInfo)) {
                return null
            }
            return GsonUtils.fromJson(userInfo, UserData::class.java)
        }

        /**
         * 是否登录
         */
        fun isLogin(): Boolean {
            return getUser() != null
        }

        /**
         * 获取Token
         */
        fun getToken(): String {
            return if (getUser() == null) "" else getUser()!!.token
        }

        /**
         * 退出登录
         */
        fun logout() {
            MMKvUtils.removeKey(USER_INFO)
        }
    }
}