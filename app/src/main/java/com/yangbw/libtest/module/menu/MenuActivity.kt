package com.yangbw.libtest.module.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseActivity
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityMenuBinding
import com.yangbw.libtest.utils.SmartUtils
import com.yangbw.libtest.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_menu.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class MenuActivity : BaseActivity<MenuViewModel, ActivityMenuBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, MenuActivity::class.java)
            })
        }
    }

    private var mOffset = 0
    private var mScrollY = 0

    override fun getLayoutId() = R.layout.activity_menu

    override fun getReplaceView(): View = refreshLayout

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .fitsSystemWindows(false)
            .init()
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setMargin(this, header)
        //toolbar
        ActionBarUtils.setSupportActionBarWithBack(toolbar) {
            finish()
        }
        refreshLayout.setOnMultiListener(object : OnMultiListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                refreshLayout.finishRefresh(2000)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore(2000)
            }

            override fun onStateChanged(
                refreshLayout: RefreshLayout,
                oldState: RefreshState,
                newState: RefreshState
            ) {
            }

            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                mOffset = offset / 2
                parallax.translationY = mOffset - mScrollY.toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }

            override fun onHeaderReleased(
                header: RefreshHeader?,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
            }

            override fun onHeaderStartAnimator(
                header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int
            ) {
            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
            }

            override fun onFooterMoving(
                footer: RefreshFooter?, isDragging: Boolean,
                percent: Float, offset: Int, footerHeight: Int, maxDragHeight: Int
            ) {
            }

            override fun onFooterReleased(
                footer: RefreshFooter?,
                footerHeight: Int,
                maxDragHeight: Int
            ) {
            }

            override fun onFooterStartAnimator(
                footer: RefreshFooter?,
                footerHeight: Int,
                maxDragHeight: Int
            ) {
            }

            override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {
            }
        })

        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h: Int = SmartUtils.dp2px(100F)
            private val color: Int = ContextCompat.getColor(
                applicationContext,
                R.color.colorPrimary
            ) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView?, scrollX: Int,
                scrollY: Int, oldScrollX: Int, oldScrollY: Int
            ) {
                var y = scrollY
                if (lastScrollY < h) {
                    y = Math.min(h, y)
                    mScrollY = if (y > h) h else y
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    parallax.translationY = mOffset - mScrollY.toFloat()
                }
                lastScrollY = y
            }
        })
        buttonBarLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
    }

}
