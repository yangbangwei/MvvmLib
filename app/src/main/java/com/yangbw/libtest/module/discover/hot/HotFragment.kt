package com.yangbw.libtest.module.discover.hot

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.library.common.base.BaseListFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
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

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun loadPageListData(pageNo: Int) {

    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun getRecyclerView(): RecyclerView = recyclerView

    override fun getAdapter() {
        mAdapter = HotAdapter()
    }
}
