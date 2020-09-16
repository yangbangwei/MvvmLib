package com.yangbw.libtest.module.msg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseActivity
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityMsgBinding
import kotlinx.android.synthetic.main.activity_msg.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class MsgActivity : BaseActivity<MsgViewModel, ActivityMsgBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, MsgActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_msg

    override fun getReplaceView(): View = activity_msg

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
        ActionBarUtils.setToolBarTitleText(toolbar, "消息中心")

    }

}
