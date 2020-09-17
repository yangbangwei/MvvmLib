package com.yangbw.libtest.module.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.library.common.base.BaseActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ActivityDetailsBinding
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class DetailsActivity : BaseActivity<DetailsViewModel, ActivityDetailsBinding>() {

    companion object {
        val ID = "id"

        fun launch(context: Context, id: String) {
            context.startActivity(Intent().apply {
                putExtra(ID, id)
                setClass(context, DetailsActivity::class.java)
            })
        }
    }

    private  var mId: String? = null

    override fun getLayoutId() = R.layout.activity_details

    override fun getReplaceView(): View = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        mId = intent.getStringExtra(ID)
        ActionBarUtils.setCenterTitleText(toolbar, "测试")
        ActionBarUtils.setSupportActionBarWithBack(toolbar) {
            finish()
        }
        mViewModel.mDetails.observe(this, {
            mBinding.detail = it
        })
        mId?.let {
            mViewModel.getHomeDetails(it)
        }
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? = smartRefreshLayout

    override fun refreshData() {
        mId?.let {
            mViewModel.getHomeDetails(it)
        }
    }
}
