package com.yangbw.libtest.module.userInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.library.common.utils.ActivityUtils
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityUserInfoBinding
import com.yangbw.libtest.module.login.LoginActivity
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class UserInfoActivity : BaseActivity<UserInfoViewModel, ActivityUserInfoBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, UserInfoActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_user_info;

    override fun getReplaceView(): View = activity_user_info

    override fun getStatusBarColor() = R.color.white

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setSupportActionBarWithBack(toolbar, R.mipmap.icon_common_back_black) {
            finish()
        }
        ActionBarUtils.setToolBarTitleText(toolbar, "账号管理")
        //点击事件
        mBinding?.run {
            setOnClickListener(groupWeixin, groupAvatar, groupName, groupPhone, tvExit) {
                when (this) {
                    groupAvatar -> {
                    }
                    groupName -> {
                    }
                    groupWeixin -> {
                    }
                    groupPhone -> {
                    }
                    tvExit -> {
                        ActivityUtils.get()?.finishAll()
                        LoginActivity.launch(mContext)
                    }
                    tvLogout -> {

                    }
                }
            }
        }
    }

}
