package com.yangbw.libtest.module.discover

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.common.CommonViewModel
import com.yangbw.libtest.common.VpAdapter
import com.yangbw.libtest.databinding.FragmentDiscoverBinding
import com.yangbw.libtest.module.discover.hot.HotFragment
import com.yangbw.libtest.module.discover.newest.NewestFragment
import com.yangbw.libtest.module.discover.recommend.RecommendFragment
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.layout_main_page_title_bar.*

/**
 * 发现模块
 *
 * @author yangbw
 * @date
 */
class DiscoverFragment : BaseFragment<CommonViewModel, FragmentDiscoverBinding>() {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private var offscreenPageLimit = 1

    override fun getLayoutId() = R.layout.fragment_discover

    override fun getReplaceView(): View = fragment_discover

    override fun initImmersionBar() {
        immersionBar {
            statusBarColor(R.color.main_color)
            autoStatusBarDarkModeEnable(true)
            titleBarMarginTop(tabLayout)
        }

    }
    override fun init(savedInstanceState: Bundle?) {
        viewPager.offscreenPageLimit = offscreenPageLimit
        viewPager.adapter = VpAdapter(requireActivity()).apply {
            addFragments(
                arrayOf(
                    HotFragment.newInstance(),
                    RecommendFragment.newInstance(),
                    NewestFragment.newInstance()
                )
            )
        }
        tabLayout.setTabData(ArrayList<CustomTabEntity>().apply {
            add(TabEntity(getString(R.string.discover_hot)))
            add(TabEntity(getString(R.string.discover_recommend)))
            add(TabEntity(getString(R.string.discover_newest)))
        })
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.currentTab = position
            }
        })

//        viewPager.currentItem = 1
    }
}
