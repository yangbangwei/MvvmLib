package com.yangbw.libtest.module.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import com.android.aachulk.consts.LiveEventBusKey
import com.blankj.utilcode.util.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.library.common.utils.ActivityUtils
import com.yangbw.libtest.R
import com.yangbw.libtest.base.CommonViewModel
import com.yangbw.libtest.module.discover.DiscoverFragment
import com.yangbw.libtest.module.goods.GoodsFragment
import com.yangbw.libtest.module.home.HomeFragment
import com.yangbw.libtest.module.login.LoginActivity
import com.yangbw.libtest.module.mine.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_bar.*

class MainActivity : BaseActivity<CommonViewModel, ViewDataBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, MainActivity::class.java)
            })
        }
    }

    private var mBackPressTime = 0L

    private var mHomeFragment: HomeFragment? = null

    private var mMsgFragment: GoodsFragment? = null

    private var mVideoFragment: DiscoverFragment? = null

    private var mMineFragment: MineFragment? = null

    override fun getLayoutId() = R.layout.activity_main

    override fun getReplaceView(): View = homeActivityFragContainer

    override fun init(savedInstanceState: Bundle?) {
        setOnClickListener(btnHome, btnDiscover, btnMine, btnGoods, btnMenu) {
            if (this.id == btnMine.id) {

            } else {
                setTabSelection(this.id)
            }
        }
        setTabSelection(btnHome.id)

        //强制登出
        LiveEventBus
            .get(LiveEventBusKey.LOGIN_OUT, String::class.java)
            .observe(this, {
                ActivityUtils.get()?.finishAll()
                LoginActivity.launch(mContext)
            })
    }

    /**
     * 切换tab
     */
    private fun setTabSelection(index: Int) {
        clearAllSelected()
        supportFragmentManager.beginTransaction().apply {
            hideFragments(this)
            when (index) {
                btnHome.id -> {
                    ivHome.isSelected = true
                    tvHome.isSelected = true
                    if (mHomeFragment == null) {
                        mHomeFragment = HomeFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mHomeFragment!!)
                    } else {
                        show(mHomeFragment!!)
                    }
                }
                btnGoods.id -> {
                    ivGoods.isSelected = true
                    tvGoods.isSelected = true
                    if (mMsgFragment == null) {
                        mMsgFragment = GoodsFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mMsgFragment!!)
                    } else {
                        show(mMsgFragment!!)
                    }
                }
                btnDiscover.id -> {
                    ivDiscover.isSelected = true
                    tvDiscover.isSelected = true
                    if (mVideoFragment == null) {
                        mVideoFragment = DiscoverFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mVideoFragment!!)
                    } else {
                        show(mVideoFragment!!)
                    }
                }
                btnMine.id -> {
                    ivMine.isSelected = true
                    tvMine.isSelected = true
                    if (mMineFragment == null) {
                        mMineFragment = MineFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mMineFragment!!)
                    } else {
                        show(mMineFragment!!)
                    }
                }
            }
        }.commitAllowingStateLoss()
    }

    /**
     * 隐藏其他tab
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        transaction.run {
            if (mHomeFragment != null) hide(mHomeFragment!!)
            if (mMsgFragment != null) hide(mMsgFragment!!)
            if (mVideoFragment != null) hide(mVideoFragment!!)
            if (mMineFragment != null) hide(mMineFragment!!)
        }
    }

    /**
     * 清空选中效果
     */
    private fun clearAllSelected() {
        ivHome.isSelected = false
        tvHome.isSelected = false
        ivGoods.isSelected = false
        tvGoods.isSelected = false
        ivDiscover.isSelected = false
        tvDiscover.isSelected = false
        ivMine.isSelected = false
        tvMine.isSelected = false
    }

    /**
     * 退出提醒
     */
    override fun onBackPressed() {
        val now = System.currentTimeMillis()
        if (now - mBackPressTime > 2000) {
            ToastUtils.showShort("再点击一次退出朴朴超市")
            mBackPressTime = now
        } else {
            super.onBackPressed()
        }
    }
}