package com.yangbw.libtest.module.discover

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentDiscoverBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class DiscoverAdapter : BaseAdapter<DiscoverListData>(R.layout.item_fragment_discover, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentDiscoverBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: DiscoverListData) {
        val itemListBinding = helper.getBinding<ItemFragmentDiscoverBinding>()
        if (itemListBinding != null) {

        }
    }

}
