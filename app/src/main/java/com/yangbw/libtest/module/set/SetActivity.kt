package com.yangbw.libtest.module.set

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.library.common.view.CommonDialog
import com.yangbw.libtest.R
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.databinding.ActivitySetBinding
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * 设置
 *
 * @author :yangbw
 * @date :2020/9/21
 */
class SetActivity : BaseActivity<SetViewModel, ActivitySetBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, SetActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_set

    override fun getReplaceView(): View = activity_set

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
        ActionBarUtils.setToolBarTitleText(toolbar, getString(R.string.set))

        mBinding.run {
            setOnClickListener(groupSetPwd, tvExit) {
                when (this) {
                    tvExit -> {
                        CommonDialog.Builder(mContext)
                            .setMessage(getString(R.string.exit_tips))
                            .setNegativeButton(getString(R.string.dialog_cancel), null)
                            .setPositiveButton(getString(R.string.dialog_confirm),
                                object : CommonDialog.OnDialogClickListener {
                                    override fun onClick(dialog: Dialog?, which: Int) {
                                        UserInfoUtils.logout()
                                        //退出登录广播
                                        LiveEventBus
                                            .get(LiveEventBusKey.LOGIN_OUT)
                                            .post("")
                                        finish()
                                    }
                                }).show()
                    }
                }
            }
        }
    }

}
