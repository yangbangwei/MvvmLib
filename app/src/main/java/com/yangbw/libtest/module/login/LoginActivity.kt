package com.yangbw.libtest.module.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.library.common.utils.ActivityUtils
import com.yangbw.libtest.R
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.databinding.ActivityLoginBinding
import com.yangbw.libtest.dialog.LoginDialog
import com.yangbw.libtest.module.register.PhoneActivity
import com.yangbw.libtest.utils.CommonUtils
import com.yangbw.libtest.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_login.*


/**
 * 一键登录
 *
 * @author :yangbw
 * @date :2020/9/18
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, LoginActivity::class.java)
            })
        }
    }

    private val mPhone = "18827777277"

    override fun getLayoutId() = R.layout.activity_login

    override fun getReplaceView(): View = activity_login

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            statusBarColor(com.library.common.R.color.white)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.run {
            //点击事件
            setOnClickListener(ivClose, ivService, btnOneLogin, tvOther, ivWchat) {
                when (this) {
                    ivClose -> finish()
                    ivService -> showToast(context.getString(R.string.service_tips))
                    btnOneLogin -> {
                        val loginDialog = LoginDialog.newInstance()
                        loginDialog.setOnOkClickListener {
                            mViewModel.login(mPhone)
                        }
                        loginDialog.show(supportFragmentManager, LoginDialog::javaClass.name)
                    }
                    ivWchat -> showToast(context.getString(R.string.wchat_login_tips))
                    tvOther -> {
                        PhoneActivity.launch(mContext)
                    }
                }
            }
            //界面初始化
            tvPhone.text = (mPhone.substring(0, 3) + "****" + mPhone.substring(7))
            CommonUtils.setAgreementTxt(mContext, tvAgreement)
        }
        mViewModel.mUser.observe(this, {
            it.let {
                UserInfoUtils.setUser(it)
                showToast(getString(R.string.login_suc))
                //发送广播
                LiveEventBus.get(LiveEventBusKey.LOGIN_SUC)
                    .post(it)
                finish()
            }

        })
        //短信验证码登录成功，关闭该页面
        LiveEventBus.get(LiveEventBusKey.LOGIN_SUC, UserData::class.java)
            .observe(this, {
                finish()
            })
    }


}
