package com.yangbw.libtest.ui.fragment

import android.os.Bundle
import android.view.View
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentHotBinding
import com.yangbw.libtest.entity.HotListData
import com.yangbw.libtest.ui.activity.WebActivity
import com.yangbw.libtest.ui.adapter.HotAdapter
import com.yangbw.libtest.ui.viewmodel.HotViewModel
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * @author yangbw
 * @date
 */
class HotFragment : BaseListFragment<HotViewModel, FragmentHotBinding,
        HotAdapter, HotListData>() {

    companion object {
        fun newInstance() = HotFragment()
    }

    override fun getLayoutId() = R.layout.fragment_hot

    override fun getReplaceView(): View = fragment_hot

    override fun immersionBarEnabled() = false

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = HotAdapter()
        mAdapter.setOnItemClickListener { _, _, _ ->
            WebActivity.launch(mContext,null,"https://home.meishichina.com/recipe-559721.html")
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        loadPageListData(1)
    }

    override fun loadPageListData(pageNo: Int) {
        mViewModel.discoverHot(pageNo)
    }

}
