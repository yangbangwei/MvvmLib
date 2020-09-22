package com.yangbw.libtest.module.userInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseActivity
import com.library.common.extension.getTextToString
import com.library.common.extension.isEmpty
import com.library.common.extension.setSelectionToEnd
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityChangeNameBinding
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_change_name.*
import kotlinx.android.synthetic.main.layout_set_toolbar.*
import utils.ActionBarUtils

/**
 * 修改昵称
 *
 * @author :yangbw
 * @date :2020/9/22
 */
class ChangeNameActivity : BaseActivity<ChangeNameViewModel, ActivityChangeNameBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, ChangeNameActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_change_name

    override fun getReplaceView(): View = activity_change_name

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
        ActionBarUtils.setToolBarTitleText(toolbar, getString(R.string.change_name))
        mBinding.run {
            etName.setText(UserInfoUtils.getUser()!!.username)
            etName.setSelectionToEnd()
            tvSave.setOnClickListener {
                if (etName.isEmpty()) {
                    showToast(R.string.change_tips)
                    return@setOnClickListener
                }
                mViewModel.changeName(etName.getTextToString())
            }
        }
        mViewModel.mResult.observe(this) {
            if (it) {
                showToast(R.string.change_suc)
                finish()
            }
        }

    }

}
