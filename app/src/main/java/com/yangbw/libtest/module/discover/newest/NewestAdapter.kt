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

    enum class TYPE(val type: Int) {
        HEAD(0),
        BODY(1)
    }

    init {
        addItemType()
    }

    override fun addItemType() {
        addItemType(TYPE.HEAD.type, R.layout.item_fragment_newest_head)
        addItemType(TYPE.BODY.type, R.layout.item_fragment_newest)
    }

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        when (viewType) {
            TYPE.HEAD.type -> {
                DataBindingUtil.bind<ItemFragmentNewestHeadBinding>(viewHolder.itemView)
            }
            TYPE.BODY.type -> {
                DataBindingUtil.bind<ItemFragmentNewestBinding>(viewHolder.itemView)
            }
        }
    }

    /**
     * 布局
     */
    override fun convert(helper: CommonViewHolder, item: NewestListData) {
        when (helper.layoutPosition) {
            TYPE.HEAD.type -> {
            }
            else -> {
                val itemListBinding = helper.getBinding<ItemFragmentNewestBinding>()
                if (itemListBinding != null) {
                    itemListBinding.data = item
                }
            }
        }
    }


}
