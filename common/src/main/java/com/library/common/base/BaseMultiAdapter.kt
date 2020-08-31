package com.library.common.base

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.library.common.view.baseviewholder.CommonViewHolder

/**
 * @author yangbw
 * @date 2020/8/31
 */
abstract class BaseMultiAdapter<T : MultiItemEntity>(data: MutableList<T>) :

    BaseMultiItemQuickAdapter<T, CommonViewHolder>(data) {

    abstract fun addItemType()

    init {
        addItemType()
    }

    open fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

}