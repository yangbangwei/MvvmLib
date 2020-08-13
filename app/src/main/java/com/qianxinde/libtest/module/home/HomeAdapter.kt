package com.qianxinde.libtest.module.home

import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.ItemFragmentMsgBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class HomeAdapter : BaseAdapter<HomeData>(R.layout.item_fragment_msg, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentMsgBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: HomeData) {
        val itemListBinding = helper.getBinding<ItemFragmentMsgBinding>()
        if (itemListBinding != null) {
            itemListBinding.data = item
        }
    }

}
