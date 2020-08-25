package com.yangbw.libtest.module.goods

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yangbw.libtest.databinding.FragmentDiscoverBinding
import kotlinx.android.synthetic.main.fragment_goods.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class GoodsFragment : BaseListFragment<GoodsViewModel, FragmentDiscoverBinding,
        GoodsAdapter, GoodsListData>() {

    companion object {
        fun newInstance() = GoodsFragment()
    }

    override fun getLayoutId() = R.layout.fragment_goods

    override fun getReplaceView(): View = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .titleBar(toolbar)
            .init()
        ActionBarUtils.setCenterTitleText(toolbar, "消息")
    }

    override fun loadPageListData(pageNo: Int) {

    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun getRecyclerView(): RecyclerView = recyclerView

    override fun getAdapter() {
        mAdapter = GoodsAdapter()
    }
}
