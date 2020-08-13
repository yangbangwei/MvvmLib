package com.qianxinde.libtest.module.home;

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseListFragment
import com.qianxinde.libtest.R
import com.qianxinde.libtest.module.common.WebActivity
import com.qianxinde.libtest.module.details.DetailsActivity
import com.qianxinde.libtest.utils.GlideImageLoader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.stx.xhb.androidx.XBanner
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class HomeFragment : BaseListFragment<HomeViewModel, ViewDataBinding, HomeAdapter, HomeData>() {

    private lateinit var banner: XBanner

    override fun getLayoutId() = R.layout.fragment_home

    override fun getReplaceView(): View = fragment_home

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .titleBar(toolbar)
            .init()
        ActionBarUtils.setCenterTitleText(toolbar, "首页")
        //banner
        banner = XBanner(context)
        banner.minimumWidth = MATCH_PARENT
        banner.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 400)
        banner.loadImage(GlideImageLoader())
        banner.setOnItemClickListener { _, model, _, _ ->
            mContext?.let { WebActivity.launch(it, null, (model as Banner).url) }
        }
        mAdapter!!.addHeaderView(banner)
        mAdapter!!.setOnItemClickListener { _, _, position ->
            mContext?.let { DetailsActivity.launch(it, mAdapter!!.getItem(position).id) }
        }
        mViewModel.mBanners.observe(this, Observer {
            banner.setBannerData(it)
        })
        mViewModel.mVersion.observe(this, Observer {
            if (it != null) {
                showToast("当前版本：" + it.verson)
            } else {
                showToast("当前为最新版本")
            }
        })
        mViewModel.onStart()
        mViewModel.updateVersion(2)
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? = smartRefreshLayout

    override fun loadPageListData(pageNo: Int) {
        if (pageNo == 1) {
            mViewModel.onStart()
        }
        mViewModel.getHomes(pageNo)
    }

    override fun getRecyclerView(): RecyclerView? = recyclerView

    override fun getLayoutManager(): RecyclerView.LayoutManager? = LinearLayoutManager(mContext)

    override fun getAdapter() {
        mAdapter = HomeAdapter()
    }
}
