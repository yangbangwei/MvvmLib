package com.yangbw.libtest.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseActivity
import com.library.common.view.IVaryViewHelperController
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityMenuBinding
import com.yangbw.libtest.ui.adapter.MenuAdapter
import com.yangbw.libtest.ui.viewmodel.MenuViewModel
import com.yangbw.libtest.view.MVaryViewHelperController
import com.yangbw.libtest.view.callback.AutoPlayPageChangeListener
import kotlinx.android.synthetic.main.activity_menu.*

/**
 * @author yangbw
 * @date
 */
class MenuActivity : BaseActivity<MenuViewModel, ActivityMenuBinding>() {

    companion object {
        fun launch(context: Activity) {
            context.startActivity(Intent().apply {
                setClass(context, MenuActivity::class.java)
            })
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }

    override fun getLayoutId() = R.layout.activity_menu

    override fun getReplaceView(): View = activity_menu

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            fitsSystemWindows(true)
            barColor(R.color.black)
        }
    }

    /**
     * 自定义相关不同状态展示的view,这里只是修改了loading的效果不同，可根据每个项目的实际情况
     * @return
     */
    override fun initVaryViewHelperController(): IVaryViewHelperController {
        return MVaryViewHelperController(getReplaceView())
    }

    override fun init(savedInstanceState: Bundle?) {
        val adapter = MenuAdapter()
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.offscreenPageLimit = 1
        //加载更多
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == adapter.data.size - 1) {
                    mViewModel.menuList()
                }
            }
        })
        //自动播放
        viewPager.registerOnPageChangeCallback(
            AutoPlayPageChangeListener(
                viewPager,
                0, R.id.videoPlayer
            )
        )

        mViewModel.mMenuData.observe(this, {
            adapter.addData(it)
        })

        mViewModel.menuList()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }
}
