package com.yangbw.libtest.module.mine;

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentMineBinding
import com.yangbw.libtest.module.msg.MsgActivity
import com.yangbw.libtest.module.set.SetActivity
import com.yangbw.libtest.module.userInfo.UserInfoActivity
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * @author yangbw
 * @date
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun getLayoutId() = R.layout.fragment_mine

    override fun getReplaceView(): View = fragment_mine

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(false)
            .fitsSystemWindows(false)
            .init()
        //点击事件
        mBinding?.run {
            setOnClickListener(groupUserInfo, ivSet,ivMsg) {
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
        }
        banner.let {
            it.addBannerLifecycleObserver(this)
            it.indicator = RectangleIndicator(mContext)
            it.setIndicatorSelectedWidth(BannerUtils.dp2px(12f).toInt())
            it.setIndicatorSpace(BannerUtils.dp2px(4f).toInt())
            it.setIndicatorRadius(0)
        }
        mViewModel.mUserInfo.observe(this, {
            banner.adapter = it.banners?.let { it1 -> MineAdapter(it1) }
            mBinding!!.mineData = it
        })
        mViewModel.getUserInfo()

    }
}
