package com.yangbw.libtest.module.home;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.module.common.WebActivity
import com.yangbw.libtest.module.details.DetailsActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class HomeFragment : BaseListFragment<HomeViewModel, ViewDataBinding, HomeAdapter, HomeData>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getReplaceView(): View = fragment_home

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .titleBar(toolbar)
            .init()
        ActionBarUtils.setCenterTitleText(toolbar, "首页")
        //banner
        val view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home_head, null)
        val banner = view.findViewById<Banner<BannerInfo, HomeBannerAdapter>>(R.id.banner)
        mAdapter!!.addHeaderView(view)
        mAdapter!!.setOnItemClickListener { _, _, position ->
            mContext?.let { DetailsActivity.launch(it, mAdapter!!.getItem(position).id) }
        }
        //添加生命周期观察者
        banner.addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(mContext)
        mViewModel.mBanners.observe(this, {
            banner.adapter = HomeBannerAdapter(it)
            banner.adapter.setOnBannerListener { data, _ ->
                mContext?.let { it1 ->
                    WebActivity.launch(it1, null, (data as BannerInfo).url)
                }
            }
        })
        mViewModel.mVersion.observe(this, {
            if (it != null) {
                showToast("当前版本：" + it.verson)
            } else {
                showToast("当前为最新版本")
            }
        })
        mViewModel.updateVersion(2)
        mViewModel.onStart()
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? = smartRefreshLayout

    override fun loadPageListData(pageNo: Int) {
        if (pageNo == 1) {
            mViewModel.onStart()
        }
        mViewModel.getHomes(pageNo)
    }

    override fun getRecyclerView(): RecyclerView? = recyclerView

    override fun getAdapter() {
        mAdapter = HomeAdapter()
    }
}
