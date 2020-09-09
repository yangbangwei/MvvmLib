package com.yangbw.libtest.module.goods

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseListFragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentDiscoverBinding
import com.yangbw.libtest.module.discover.newest.NewestAdapter
import kotlinx.android.synthetic.main.fragment_goods.*
import kotlinx.android.synthetic.main.fragment_goods.recyclerView
import kotlinx.android.synthetic.main.fragment_goods.smartRefreshLayout
import kotlinx.android.synthetic.main.fragment_newest.*
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
