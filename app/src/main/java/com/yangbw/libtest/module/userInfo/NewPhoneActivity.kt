package com.yangbw.libtest.module.userInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.library.common.base.BaseActivity
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityNewPhoneBinding
import kotlinx.android.synthetic.main.activity_new_phone.*

/**
 * 验证新手机号
 *
 * @author :yangbw
 * @date :2020/9/22
 */
class NewPhoneActivity : BaseActivity<NewPhoneViewModel, ActivityNewPhoneBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, NewPhoneActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_new_phone

    override fun getReplaceView(): View = activity_new_phone

    override fun init(savedInstanceState: Bundle?) {


    }

}
