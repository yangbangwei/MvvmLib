package com.yangbw.libtest.ui.adapter

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentClassBinding
import com.yangbw.libtest.entity.BannerInfo

/**
 * @author yangbw
 * @date
 */
class ClassAdapter : BaseAdapter<BannerInfo.Data>(R.layout.item_fragment_class, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentClassBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: BannerInfo.Data) {
        val itemListBinding = helper.getBinding<ItemFragmentClassBinding>()
        if (itemListBinding != null) {
            itemListBinding.data = item
        }
    }

}
