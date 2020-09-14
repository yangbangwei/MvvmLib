package com.yangbw.libtest.module.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityMenuBinding
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

    override fun getStatusBarColor() = R.color.black

    override fun init(savedInstanceState: Bundle?) {


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
