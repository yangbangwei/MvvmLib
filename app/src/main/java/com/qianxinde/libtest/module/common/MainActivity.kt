package com.qianxinde.libtest.module.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.android.aachulk.consts.LiveEventBusKey
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ImmersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.utils.ActivityUtils
import com.qianxinde.libtest.R
import com.qianxinde.libtest.base.CommonViewModel
import com.qianxinde.libtest.module.home.HomeFragment
import com.qianxinde.libtest.module.login.LoginActivity
import com.qianxinde.libtest.module.mine.MineFragment
import com.qianxinde.libtest.module.msg.MsgFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<CommonViewModel, ViewDataBinding>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, MainActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun getReplaceView(): View = viewpager

    override fun init(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener(this)
        viewpager.offscreenPageLimit = 3
        viewpager.adapter = object :
            FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return TabFragment.values().size
            }

            override fun getItem(position: Int): Fragment {
                return TabFragment.values()[position].fragment()
            }
        }
        /**
         * viewpager左右滑的切换
         */
        viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }
        })

        //LiveEventBus接受数据
        LiveEventBus
            .get(LiveEventBusKey.LOGIN_OUT, String::class.java)
            .observe(this, Observer<String?> {
                ActivityUtils.get()?.finishAll()
                LoginActivity.launch(mContext)
            })
    }

    /**
     * BottomNavigationView的选中
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        (findViewById<ViewPager>(R.id.viewpager)).currentItem = TabFragment.from(
            menuItem.itemId
        ).ordinal
        return false
    }

    /**
     * TabFragment
     *
     */

    private enum class TabFragment(@IdRes menuId: Int, clazz: Class<out Fragment>) {
        Home(R.id.navigation_home, HomeFragment::class.java),
        Dashboard(R.id.navigation_dashboard, MsgFragment::class.java),
        Notifications(R.id.navigation_notifications, MineFragment::class.java);

        private var fragment: Fragment? = null
        private val menuId: Int = menuId
        private val clazz: Class<out Fragment> = clazz
        fun fragment(): Fragment {
            if (fragment == null) {
                fragment = try {
                    clazz.newInstance()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
            return fragment!!
        }

        companion object {
            fun from(itemId: Int): TabFragment {
                for (fragment in TabFragment.values()) {
                    if (fragment.menuId == itemId) {
                        return fragment
                    }
                }
                return Home
            }

            fun onDestroy() {
                for (fragment in TabFragment.values()) {
                    fragment.fragment = null
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        TabFragment.onDestroy()
    }
}