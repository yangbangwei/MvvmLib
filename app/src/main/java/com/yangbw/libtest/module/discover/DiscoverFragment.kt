package com.yangbw.libtest.module.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.base.CommonViewModel
import com.yangbw.libtest.databinding.FragmentDiscoverBinding
import com.yangbw.libtest.module.discover.hot.HotFragment
import com.yangbw.libtest.module.discover.hot.TabEntity
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.layout_main_page_title_bar.*

/**
 * @author yangbw
 * @date
 */
class DiscoverFragment : BaseFragment<CommonViewModel, FragmentDiscoverBinding>() {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private var offscreenPageLimit = 1

    private var pageChangeCallback: PageChangeCallback? = null

    override fun getLayoutId() = R.layout.fragment_discover

    override fun getReplaceView(): View = fragment_discover

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .titleBar(tabLayout)
            .init()
        viewPager.offscreenPageLimit = offscreenPageLimit
        viewPager.adapter = VpAdapter(requireActivity()).apply {
            addFragments(
                arrayOf(
                    HotFragment.newInstance(),
                    HotFragment.newInstance(),
                    HotFragment.newInstance()
                )
            )
        }
        tabLayout.setTabData(ArrayList<CustomTabEntity>().apply {
            add(TabEntity("热门"))
            add(TabEntity("推荐"))
            add(TabEntity("最新"))
        })
        viewPager.currentItem = 1
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
        pageChangeCallback = PageChangeCallback()
        viewPager.registerOnPageChangeCallback(pageChangeCallback!!)
    }

    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tabLayout.currentTab = position
        }
    }

    class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }

    override fun onDestroy() {
        super.onDestroy()
        pageChangeCallback?.run { viewPager?.unregisterOnPageChangeCallback(this) }
        pageChangeCallback = null
    }
}
