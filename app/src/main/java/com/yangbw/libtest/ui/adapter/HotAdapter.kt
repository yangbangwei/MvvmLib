package com.yangbw.libtest.ui.adapter

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentHotBinding
import com.yangbw.libtest.entity.HotListData

/**
 * @author yangbw
 * @date
 */
class HotAdapter : BaseAdapter<HotListData>(R.layout.item_fragment_hot, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentHotBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: HotListData) {
        val itemListBinding = helper.getBinding<ItemFragmentHotBinding>()
        if (itemListBinding != null) {
            itemListBinding.data = item
        }
    }

}
