package com.yangbw.libtest.module.discover.newest

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseMultiAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentNewestBinding
import com.yangbw.libtest.databinding.ItemFragmentNewestHeadBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class NewestAdapter : BaseMultiAdapter<NewestListData>(ArrayList()) {

    init {
        addItemType(NewestListData.TYPE_HEAD, R.layout.item_fragment_newest_head)
        addItemType(NewestListData.TYPE_BODY, R.layout.item_fragment_newest)
    }

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        when (viewType) {
            NewestListData.TYPE_HEAD  -> {
                DataBindingUtil.bind<ItemFragmentNewestHeadBinding>(viewHolder.itemView)
            }
            NewestListData.TYPE_BODY -> {
                DataBindingUtil.bind<ItemFragmentNewestBinding>(viewHolder.itemView)
            }
        }
    }

    override fun convert(helper: CommonViewHolder, item: NewestListData) {
        when (item.itemType) {
            NewestListData.TYPE_HEAD -> {

            }
            NewestListData.TYPE_BODY -> {
                val itemListBinding = helper.getBinding<ItemFragmentNewestBinding>()
                if (itemListBinding != null) {
                    itemListBinding.data = item
                }
            }
        }
    }


}
