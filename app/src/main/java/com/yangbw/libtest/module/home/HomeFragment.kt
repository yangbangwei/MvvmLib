package com.yangbw.libtest.module.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.alibaba.android.vlayout.layout.StickyLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.library.common.utils.GlideUtils
import com.sunfusheng.marqueeview.MarqueeView
import com.yangbw.libtest.R
import com.yangbw.libtest.module.common.WebActivity
import com.yangbw.libtest.module.discover.DiscoverFragment
import com.yangbw.libtest.module.discover.hot.HotFragment
import com.yangbw.libtest.module.discover.hot.TabEntity
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author yangbw
 * @date
 */
class HomeFragment : BaseFragment<HomeViewModel, ViewDataBinding>() {

    private lateinit var mAdapters: MutableList<DelegateAdapter.Adapter<*>>

    companion object {
        const val BANNER = 1
        const val CLASS = 2
        const val OTHER_CLASS = 3
        const val TIPS = 4
        const val PROMOTION = 5

        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getReplaceView(): View = frameLayout

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
        viewPool.setMaxRecycledViews(0, 30)

        //首页动态布局
        mViewModel.mBanners.observe(this, {
            mAdapters = ArrayList()
            getBanner(it)
            val delegateAdapter = DelegateAdapter(layoutManager, true)
            recyclerView.adapter = delegateAdapter
            delegateAdapter.setAdapters(mAdapters)
        })
        //更新接口
        mViewModel.mVersion.observe(this, {
            if (it != null) {
                showToast("当前版本：${it.verson}")
            } else {
                showToast("当前为最新版本")
            }
        })

        mViewModel.updateVersion(2)
        refreshData()
    }

    override fun refreshData() {
        mViewModel.onStart()
    }

    /**
     * 获取首页动态布局
     */
    private fun getBanner(list: List<BannerInfo>) {
        list.forEach {
            when (it.type) {
                //轮播图
                BANNER -> {
                    val adapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
                        mContext, LinearLayoutHelper(), R.layout.fragment_home_banner, 1, it.type
                    ) {
                        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                            super.onBindViewHolder(holder, position)
                            val banner =
                                holder.getView<Banner<BannerInfo, HomeBannerAdapter>>(R.id.banner)
                            banner.adapter = HomeBannerAdapter(it.datas)
                            banner.adapter.setOnBannerListener { data, _ ->
                                WebActivity.launch(mContext, null, (data as BannerInfo.Data).url)
                            }
                        }
                    }
                    mAdapters.add(adapter)
                }
                //推荐分类
                CLASS -> {
                    val adapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
                        mContext, GridLayoutHelper(5), R.layout.fragment_home_class, 10, it.type
                    ) {
                        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                            super.onBindViewHolder(holder, position)
                            val ivMenu = holder.getView<ImageView>(R.id.iv_menu)
                            val tvTitle = holder.getView<TextView>(R.id.tv_title)
                            tvTitle.text = it.datas[position].title
                            GlideUtils.loadImage(ivMenu, it.datas[position].img)
                        }
                    }
                    mAdapters.add(adapter)
                }
                //剩余分类
                OTHER_CLASS -> {
                    val adapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
                        mContext, LinearLayoutHelper(1), R.layout.fragment_home_other, 1, it.type
                    ) {
                        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                            super.onBindViewHolder(holder, position)
                            val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
                            recyclerView.layoutManager = LinearLayoutManager(
                                mContext, RecyclerView.HORIZONTAL, false
                            )
                            val classAdapter = ClassAdapter().apply {
                                data = it.datas
                            }
                            recyclerView.adapter = classAdapter
                        }
                    }
                    mAdapters.add(adapter)
                }
                //滚动画报
                TIPS -> {
                    val adapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
                        mContext, LinearLayoutHelper(1), R.layout.fragment_home_tip, 1, it.type
                    ) {
                        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                            super.onBindViewHolder(holder, position)
                            val marqueeView = holder.getView<MarqueeView>(R.id.marqueeView)
                            val info: MutableList<String> = ArrayList()
                            it.datas.forEach { data ->
                                info.add(data.title)
                            }
                            marqueeView.startWithList(info)
                        }
                    }
                    mAdapters.add(adapter)
                }
//                //活动类型
//                PROMOTION -> {
//                    val adapter: BaseDelegateAdapter = object : BaseDelegateAdapter(
//                        mContext,
//                        StickyLayoutHelper(true),
//                        R.layout.fragment_home_promotion,
//                        1,
//                        it.type
//                    ) {
//                        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//                            super.onBindViewHolder(holder, position)
//                            val tabLayout = holder.getView<CommonTabLayout>(R.id.tabLayout)
//
//                            tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
//                                override fun onTabSelect(position: Int) {
//                                }
//
//                                override fun onTabReselect(position: Int) {
//
//                                }
//                            })
//                        }
//                    }
//                    mAdapters.add(adapter)
//                }
            }
        }
    }

}
