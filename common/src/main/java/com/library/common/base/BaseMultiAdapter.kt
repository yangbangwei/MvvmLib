package com.library.common.base

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.library.common.view.baseviewholder.CommonViewHolder

/**
 * @author yangbw
 * @date 2020/3/23.
 * module：继承BaseQuickAdapter，自定义通用方法
 * description：
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