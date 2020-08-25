package com.yangbw.libtest.module.goods

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentGoodsBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class GoodsAdapter : BaseAdapter<GoodsListData>(R.layout.item_fragment_goods, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentGoodsBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: GoodsListData) {
        val itemListBinding = helper.getBinding<ItemFragmentGoodsBinding>()
        if (itemListBinding != null) {

        }
    }

}
