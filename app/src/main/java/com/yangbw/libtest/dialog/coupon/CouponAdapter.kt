package com.yangbw.libtest.dialog.coupon

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentCouponBinding
import java.util.*

/**
 * @author :yangbw
 * @date :2020/9/17
 */
class CouponAdapter : BaseAdapter<CouponData>(R.layout.item_fragment_coupon, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentCouponBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: CouponData) {
        val itemListBinding = helper.getBinding<ItemFragmentCouponBinding>()
        if (itemListBinding != null) {
            itemListBinding.item = item
        }
    }

}
