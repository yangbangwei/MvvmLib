package com.yangbw.libtest.module.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yangbw.libtest.common.Constant
import com.yangbw.libtest.common.LiveEventBusKey
import com.blankj.utilcode.util.SPUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityLoginBinding
import com.yangbw.libtest.module.common.MainActivity
import com.yangbw.libtest.module.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, LoginActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun getReplaceView(): View = activity_login

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setCenterTitleText(toolbar, "登录")

        mViewModel.mUser.observe(this, {
            it.let {
                SPUtils.getInstance().put(Constant.TOKEN,it.token)
                MainActivity.launch(mContext)
                showToast("登录成功")
                finish()
            }

        })
        mBinding?.run {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                if (username.isBlank()) {
                    showToast("请输入用户名")
                    return@setOnClickListener
                }
                val password = etPassword.text.toString()
                if (password.isBlank()) {
                    showToast("请输入密码")
                    return@setOnClickListener
                }
                mViewModel.login(username, password)
            }
            tvRegister.setOnClickListener {
                RegisterActivity.launch(mContext)
            }
        }
        LiveEventBus.get(LiveEventBusKey.REGISTER_SUC, UserData::class.java)
            .observe(this, {
                it?.let {
                    mBinding!!.etUsername.setText(it.username)
                    mBinding!!.etPassword.setText(it.password)
                }
            })
    }

}
