package com.yangbw.libtest.module.discover.recommend

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentRecommendBinding
import java.util.*

/**
 * @author yangbw
 * @date
 */
class RecommendAdapter :
    BaseAdapter<RecommendListData>(R.layout.item_fragment_recommend, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentRecommendBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: RecommendListData) {
    }

}
