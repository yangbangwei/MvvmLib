package com.qianxinde.libtest.module.mine;

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.qianxinde.libtest.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * @author yangbw
 * @date
 */
class MineFragment : BaseFragment<MineViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun getLayoutId() = R.layout.fragment_mine

    override fun getReplaceView(): View = fragment_mine

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .fitsSystemWindows(false)
            .init()
        val arrayList = ArrayList<MineData>()
        arrayList.add(MineData())
        banner.adapter = ImageAdapter(arrayList)
        //添加生命周期观察者
        banner.addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(mContext)
        mViewModel.mUserInfo.observe(this, Observer {
        })
        mViewModel.getUserInfo()
    }

    override fun refreshData() {
        mViewModel.getUserInfo()
    }
}
