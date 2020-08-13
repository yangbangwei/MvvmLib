package com.qianxinde.libtest.module.msg

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.ItemFragmentMsgBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class MsgAdapter : BaseAdapter<MsgListData>(R.layout.item_fragment_msg, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentMsgBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: MsgListData) {
        val itemListBinding = helper.getBinding<ItemFragmentMsgBinding>()
        if (itemListBinding != null) {

        }
    }

}
