package com.qianxinde.libtest.module.video

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.library.common.base.BaseListFragment
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.FragmentVideoBinding
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_video.*

/**
 * @author yangbw
 * @date
 */
class VideoFragment : BaseListFragment<VideoViewModel, FragmentVideoBinding,
        VideoAdapter, VideoListData>() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun getLayoutId() = R.layout.fragment_video

    override fun getReplaceView(): View = fragment_video

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun loadPageListData(pageNo: Int) {

    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout

    override fun getRecyclerView(): RecyclerView = recyclerView

    override fun getAdapter() {
        mAdapter = VideoAdapter()
    }
}
