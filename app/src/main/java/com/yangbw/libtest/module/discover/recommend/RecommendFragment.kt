package com.yangbw.libtest.module.discover.recommend

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.library.common.base.BaseListFragment
import com.library.common.extension.dp2px
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

    override fun getReplaceView(): View = fragment_recommend

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = mainLayoutManager
        mAdapter = RecommendAdapter()
    }

    override fun init(savedInstanceState: Bundle?) {
        loadPageListData(1)
    }

    override fun loadPageListData(pageNo: Int) {
        mViewModel.discoverRecommend(pageNo)
    }

}
