package com.yangbw.libtest.module.discover

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.library.common.base.BaseListFragment
import com.yangbw.libtest.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yangbw.libtest.databinding.FragmentDiscoverBinding
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * @author yangbw
 * @date
 */
class DiscoverFragment : BaseListFragment<DiscoverViewModel, FragmentDiscoverBinding,
        DiscoverAdapter, DiscoverListData>() {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override fun getLayoutId() = R.layout.fragment_discover

    override fun getReplaceView(): View = fragment_video

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun loadPageListData(pageNo: Int) {

    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun getRecyclerView(): RecyclerView = recyclerView

    override fun getAdapter() {
        mAdapter = DiscoverAdapter()
    }
}
