package com.yangbw.libtest.module.discover.recommend

import android.os.Bundle
import android.view.View
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentRecommendBinding
import kotlinx.android.synthetic.main.fragment_recommend.*

/**
 * @author yangbw
 * @date
 */
class RecommendFragment : BaseListFragment<RecommendViewModel, FragmentRecommendBinding,
        RecommendAdapter, RecommendListData>() {

    companion object {
        fun newInstance() = RecommendFragment()
    }

    override fun getLayoutId() = R.layout.fragment_recommend

    override fun getReplaceView(): View = smartRefreshLayout

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = RecommendAdapter()
    }

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun loadPageListData(pageNo: Int) {

    }

}
