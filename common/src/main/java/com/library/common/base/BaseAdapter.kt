package com.library.common.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.library.common.view.baseviewholder.CommonViewHolder

/**
 * 基础BaseAdapter封装
 *
 * @author yangbw
 * @date 2020/8/31
 */
abstract class BaseAdapter<T>(layoutResId: Int, data: MutableList<T>) :

    BaseQuickAdapter<T, CommonViewHolder>(layoutResId, data) {

    open fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

}