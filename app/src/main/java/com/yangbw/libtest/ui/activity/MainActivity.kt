package com.yangbw.libtest.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import com.blankj.utilcode.util.ToastUtils
import com.library.common.base.BaseActivity
import com.library.common.extension.setOnClickListener
import com.yangbw.libtest.R
import com.yangbw.libtest.common.CommonViewModel
import com.yangbw.libtest.ui.dialog.CouponDialog
import com.yangbw.libtest.ui.fragment.DiscoverFragment
import com.yangbw.libtest.ui.fragment.GoodsFragment
import com.yangbw.libtest.ui.fragment.HomeFragment
import com.yangbw.libtest.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_bar.*

/**
 * 首页
 *
 * @author yangbw
 * @date 2020/9/1
 */
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

    private var mGoodsFragment: GoodsFragment? = null

    private var mDiscoverFragment: DiscoverFragment? = null

    private var mMineFragment: MineFragment? = null

    override fun getLayoutId() = R.layout.activity_main

    override fun getReplaceView(): View = homeActivityFragContainer

    override fun init(savedInstanceState: Bundle?) {
        setOnClickListener(btnHome, btnDiscover, btnMine, btnGoods, btnMenu) {
            if (this.id == btnMenu.id) {
                MenuActivity.launch(mActivity)
            } else {
                setTabSelection(this.id)
            }
        }
        //选中首页
        setTabSelection(btnHome.id)

        //显示弹窗
        val couponDialog = CouponDialog.newInstance()
        couponDialog.setOnOkClickListener {
            showToast(getString(R.string.congratulation))
        }
        couponDialog.show(supportFragmentManager, CouponDialog::javaClass.name)
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
                    if (mGoodsFragment == null) {
                        mGoodsFragment = GoodsFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mGoodsFragment!!)
                    } else {
                        show(mGoodsFragment!!)
                    }
                }
                btnDiscover.id -> {
                    ivDiscover.isSelected = true
                    tvDiscover.isSelected = true
                    if (mDiscoverFragment == null) {
                        mDiscoverFragment = DiscoverFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mDiscoverFragment!!)
                    } else {
                        show(mDiscoverFragment!!)
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
            if (mGoodsFragment != null) hide(mGoodsFragment!!)
            if (mDiscoverFragment != null) hide(mDiscoverFragment!!)
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
            ToastUtils.showShort(R.string.out_tip)
            mBackPressTime = now
        } else {
            super.onBackPressed()
        }
    }
}