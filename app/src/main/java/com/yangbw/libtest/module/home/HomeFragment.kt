package com.yangbw.libtest.module.home;

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yangbw.libtest.R
import com.yangbw.libtest.module.common.WebActivity
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author yangbw
 * @date
 */
class HomeFragment : BaseFragment<HomeViewModel, ViewDataBinding>() {

    private val mAdapters: MutableList<DelegateAdapter.Adapter<*>> = ArrayList()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getReplaceView(): View = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(false)
            .fitsSystemWindows(false)
            .init()
        val layoutManager = VirtualLayoutManager(mContext)
        recyclerView.layoutManager = layoutManager
        val viewPool = RecyclerView.RecycledViewPool()

        recyclerView.setRecycledViewPool(viewPool)
        viewPool.setMaxRecycledViews(0, 10)

        val delegateAdapter = DelegateAdapter(layoutManager, true)
        recyclerView.adapter = delegateAdapter
        mViewModel.mVersion.observe(this, {
            if (it != null) {
                showToast("当前版本：${it.verson}")
            } else {
                showToast("当前为最新版本")
            }
        })
        mViewModel.mBanners.observe(this, {
            //轮播图
            val bannerAdapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
                mContext, LinearLayoutHelper(), R.layout.fragment_home_banner, 1, 1
            ) {
                override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                    super.onBindViewHolder(holder, position)
                    val banner = holder.getView<Banner<BannerInfo, HomeBannerAdapter>>(R.id.banner)
                    banner.adapter = HomeBannerAdapter(it)
                    banner.adapter.setOnBannerListener { data, _ ->
                        WebActivity.launch(mContext, null, (data as BannerInfo).url)
                    }
                }
            }
            mAdapters.add(bannerAdapter)
            //分类图
            val classAdapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
                mContext, GridLayoutHelper(5), R.layout.fragment_home_class, 10, 2
            ) {
                override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                    super.onBindViewHolder(holder, position)
//                    val banner = holder.getView<Banner<BannerInfo, HomeBannerAdapter>>(R.id.iv_)
//                    banner.adapter = HomeBannerAdapter(it)
//                    banner.adapter.setOnBannerListener { data, _ ->
//                        WebActivity.launch(mContext, null, (data as BannerInfo).url)
//                    }
                }
            }
            mAdapters.add(classAdapter)
            //设置适配器
            delegateAdapter.setAdapters(mAdapters)
        })
        mViewModel.updateVersion(2)
        mViewModel.onStart()
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? = smartRefreshLayout

}
