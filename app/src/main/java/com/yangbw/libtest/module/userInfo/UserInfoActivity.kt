package com.yangbw.libtest.module.userInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.databinding.ActivityUserInfoBinding
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * 用户信息
 *
 * @author :yangbw
 * @date :2020/9/1
 */
class UserInfoActivity : BaseActivity<UserInfoViewModel, ActivityUserInfoBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, UserInfoActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_user_info

    override fun getReplaceView(): View = activity_user_info

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            statusBarColor(com.library.common.R.color.white)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setSupportActionBarWithBack(toolbar, R.mipmap.icon_common_back_black) {
            finish()
        }
        ActionBarUtils.setToolBarTitleText(toolbar, getString(R.string.user_manager))
        //点击事件
        mBinding.run {
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
                        UserInfoUtils.logout()
                        //退出登录广播
                        LiveEventBus
                            .get(LiveEventBusKey.LOGIN_OUT)
                            .post("")
                        finish()
                    }
                    tvLogout -> {

                    }
                }
            }
            mBinding.data = UserInfoUtils.getUser()
        }
    }
}
