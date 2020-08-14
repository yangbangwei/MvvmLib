package com.qianxinde.libtest.module.mine;

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.FragmentMineBinding
import com.scwang.smartrefresh.layout.SmartRefreshLayout
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
            .fitsSystemWindows(false)
            .init()
        banner.let {
            it.addBannerLifecycleObserver(this)
            it.indicator = RectangleIndicator (mContext)
            it.setIndicatorSelectedWidth(BannerUtils.dp2px(12f).toInt())
            it.setIndicatorSpace(BannerUtils.dp2px(4f).toInt())
            it.setIndicatorRadius(0)
        }
        mViewModel.mUserInfo.observe(this, Observer {
            banner.adapter = it.banners?.let { it1 -> MineAdapter(it1) }
            mBinding!!.mineData = it
        })
        mViewModel.getUserInfo()
    }
}
