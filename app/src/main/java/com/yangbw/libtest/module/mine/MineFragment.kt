package com.yangbw.libtest.module.mine

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.library.common.extension.setOnClickListener
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentMineBinding
import com.yangbw.libtest.module.msg.MsgActivity
import com.yangbw.libtest.module.set.SetActivity
import com.yangbw.libtest.module.userInfo.UserInfoActivity
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

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(false)
            .fitsSystemWindows(false)
            .init()
        mBinding.run {
            //点击事件
            setOnClickListener(groupUserInfo, ivSet, ivMsg) {
                when (this) {
                    groupUserInfo -> {
                        UserInfoActivity.launch(mContext)
                    }
                    ivSet -> {
                        SetActivity.launch(mContext)
                    }
                    ivMsg -> {
                        MsgActivity.launch(mContext)
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
                mBinding.mineData = it
            })
            mBanner.observe(this@MineFragment, {
                mBinding.banner.adapter = MineAdapter(it)
            })
        }

        mBinding.mineData = MineData(getString(R.string.login_tips), null, "-", "-", "-")
        getSmartRefreshLayout().autoRefresh()
    }

    override fun refreshData() {
        mViewModel.onStart()
    }
}
