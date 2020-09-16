package com.yangbw.libtest.module.goods

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentGoodsBinding
import kotlinx.android.synthetic.main.fragment_goods.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class GoodsFragment : BaseListFragment<GoodsViewModel, FragmentGoodsBinding,
        GoodsAdapter, GoodsListData>() {

    companion object {
        fun newInstance() = GoodsFragment()
    }

    override fun getLayoutId() = R.layout.fragment_goods

    override fun getReplaceView(): View = smartRefreshLayout

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            statusBarColor(R.color.white)
            titleBar(toolbar)
        }
    }

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = GoodsAdapter()
    }

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setToolBarTitleText(toolbar, "分类")
        ImmersionBar.with(this)
            .titleBar(toolbar)
            .init()
    }

    override fun loadPageListData(pageNo: Int) {

    }

}
