package com.yangbw.libtest.module.discover.hot

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.library.common.base.BaseListFragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentHotBinding
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

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun getRecyclerView(): RecyclerView = recyclerView

    override fun getAdapter() {
        mAdapter = HotAdapter()
        getRecyclerView().layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun init(savedInstanceState: Bundle?) {
        loadPageListData(1)
    }

    override fun loadPageListData(pageNo: Int) {
        mViewModel.discoverHot(pageNo)
    }

}
