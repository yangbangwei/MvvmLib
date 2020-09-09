package com.yangbw.libtest.module.discover.newest

import android.os.Bundle
import android.view.View
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentNewestBinding
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

    override fun getReplaceView(): View = smartRefreshLayout

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = NewestAdapter()
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun loadPageListData(pageNo: Int) {

    }

}
