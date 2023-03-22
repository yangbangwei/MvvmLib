package com.yangbw.libtest.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentRecommendBinding
import com.yangbw.libtest.entity.RecommendListData
import com.yangbw.libtest.ui.adapter.RecommendAdapter
import com.yangbw.libtest.ui.viewmodel.RecommendViewModel
import kotlinx.android.synthetic.main.fragment_recommend.*

/**
 * 推荐页面
 *
 * @author :yangbw
 * @date :2020/9/23
 */
class RecommendFragment : BaseListFragment<RecommendViewModel, FragmentRecommendBinding,
        RecommendAdapter, RecommendListData>() {

    companion object {
        fun newInstance() = RecommendFragment()
    }

    override fun getLayoutId() = R.layout.fragment_recommend

    override fun getReplaceView(): View = fragment_recommend

    override fun immersionBarEnabled() = false

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = mainLayoutManager
        mAdapter = RecommendAdapter()
        mAdapter.addChildClickViewIds(R.id.iv_like)
        mAdapter.setOnItemChildClickListener { _, _, position ->
            mViewModel.discoverRecommendLike(mAdapter.getItem(position).id)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.mLike.observe(this){
            showToast(it)
        }
        loadPageListData(1)
    }

    override fun loadPageListData(pageNo: Int) {
        mViewModel.discoverRecommend(pageNo)
    }

}
