package com.yangbw.libtest.module.mine

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseFragment
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.common.LiveEventBusKey
import com.yangbw.libtest.databinding.FragmentMineBinding
import com.yangbw.libtest.module.login.LoginActivity
import com.yangbw.libtest.module.login.UserData
import com.yangbw.libtest.module.msg.MsgActivity
import com.yangbw.libtest.module.set.SetActivity
import com.yangbw.libtest.module.userInfo.UserInfoActivity
import com.yangbw.libtest.utils.UserInfoUtils
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 我的页面
 *
 * @author yangbw
 * @date
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun getLayoutId() = R.layout.fragment_mine

    override fun getReplaceView(): View = fragment_mine

    override fun initImmersionBar() {
        immersionBar {
            transparentStatusBar()
            autoStatusBarDarkModeEnable(false)
            fitsSystemWindows(false)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.run {
            ivSet.setOnClickListener {
                SetActivity.launch(mContext)
            }
            //点击事件
            setOnClickListener(groupUserInfo, ivMsg) {
                if (!UserInfoUtils.isLogin()) {
                    LoginActivity.launch(mContext)
                } else {
                    when (this) {
                        groupUserInfo -> {
                            UserInfoActivity.launch(mContext)
                        }
                        ivMsg -> {
                            MsgActivity.launch(mContext)
                        }
                    }
                }
            }
            //广告
            banner.let {
                it.addBannerLifecycleObserver(this@MineFragment)
                it.indicator = RectangleIndicator(mContext)
                it.setIndicatorSelectedWidth(BannerUtils.dp2px(12f).toInt())
                it.setIndicatorSpace(BannerUtils.dp2px(4f).toInt())
                it.setIndicatorRadius(0)
            }
        }
        mViewModel.run {
            mUserInfo.observe(this@MineFragment, {
                if (UserInfoUtils.isLogin()) {
                    mBinding.mineData = it
                }
                mBinding.banner.adapter = MineAdapter(it.banners!!)
            })
        }

        mBinding.mineData = MineData(getString(R.string.login_tips), null, "-", "-", "-", null)
        mViewModel.onStart()

        //登录成功刷新页面
        LiveEventBus.get(LiveEventBusKey.LOGIN_SUC, UserData::class.java)
            .observe(this, {
                mViewModel.onStart()
            })

        //退出登录
        LiveEventBus
            .get(LiveEventBusKey.LOGIN_OUT, String::class.java)
            .observe(this, {
                mBinding.ivImg.setImageResource(R.mipmap.default_avatar_header)
                mBinding.mineData =
                    MineData(getString(R.string.login_tips), null, "-", "-", "-", null)
                mViewModel.onStart()
            })
    }

}
