package com.yangbw.libtest.module.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yangbw.libtest.common.LiveEventBusKey
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityRegisterBinding
import com.yangbw.libtest.module.login.UserData
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, RegisterActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_register;

    override fun getReplaceView(): View = activity_register

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setSupportActionBarWithBack(toolbar) {
            finish()
        }
        ActionBarUtils.setCenterTitleText(toolbar, "注册")
        mViewModel.mResult.observe(this, Observer {
            showToast("注册成功")
            val username = mBinding!!.etUsername.text.toString()
            val password = mBinding!!.etPassword.text.toString()
            LiveEventBus.get(LiveEventBusKey.REGISTER_SUC)
                .post(UserData(password, username))
            finish()
        })
        mBinding?.run {
            btnRegister.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val rePsw = etRePsw.text.toString()
                if (username.isBlank()) {
                    showToast(etUsername.hint.toString())
                    return@setOnClickListener
                }
                if (password.isBlank()) {
                    showToast(etPassword.hint.toString())
                    return@setOnClickListener
                }
                if (rePsw.isBlank()) {
                    showToast(etRePsw.hint.toString())
                    return@setOnClickListener
                }
                if (rePsw != password) {
                    showToast("两次输入的密码不一样，请重新输入")
                    return@setOnClickListener
                }
                mViewModel.register(username, password)
            }
        }

    }

}
