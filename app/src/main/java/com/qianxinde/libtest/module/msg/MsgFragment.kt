package com.qianxinde.libtest.module.msg

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseListFragment
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.FragmentMsgBinding
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_msg.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class MsgFragment : BaseListFragment<MsgViewModel, FragmentMsgBinding,
        MsgAdapter, MsgListData>() {

    companion object {
        fun newInstance() = MsgFragment()
    }

    override fun getLayoutId() = R.layout.fragment_msg

    override fun getReplaceView(): View = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .titleBar(toolbar)
            .init()
        ActionBarUtils.setCenterTitleText(toolbar, "消息")
    }

    override fun loadPageListData(pageNo: Int) {

    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun getRecyclerView(): RecyclerView = recyclerView

    override fun getAdapter() {
        mAdapter = MsgAdapter()
    }
}
