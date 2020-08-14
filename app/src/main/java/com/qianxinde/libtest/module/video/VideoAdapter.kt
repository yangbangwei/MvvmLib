package com.qianxinde.libtest.module.video

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.ItemFragmentVideoBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class VideoAdapter : BaseAdapter<VideoListData>(R.layout.item_fragment_video, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentVideoBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: VideoListData) {
        val itemListBinding = helper.getBinding<ItemFragmentVideoBinding>()
        if (itemListBinding != null) {

        }
    }

}
