package com.yangbw.libtest.ui.fragment

import android.os.Bundle
import android.view.View
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentNewestBinding
import com.yangbw.libtest.entity.NewestListData
import com.yangbw.libtest.ui.adapter.NewestAdapter
import com.yangbw.libtest.ui.viewmodel.NewestViewModel
import kotlinx.android.synthetic.main.fragment_newest.*

/**
 * 发现-最新
 *
 * @author :yangbw
 * @date :2020/9/9
 */
class NewestFragment : BaseListFragment<NewestViewModel, FragmentNewestBinding,
        NewestAdapter, NewestListData>() {

    companion object {
        fun newInstance() = NewestFragment()
    }

    override fun getLayoutId() = R.layout.fragment_newest

    override fun getReplaceView(): View = fragment_newest

    override fun immersionBarEnabled() = false

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = NewestAdapter()
    }

    override fun init(savedInstanceState: Bundle?) {
        loadPageListData(1)
    }

    override fun loadPageListData(pageNo: Int) {
        mViewModel.discoverNew(pageNo)
    }

}
