package com.yangbw.libtest.module.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 基类适配器
 *
 * @author :yangbw
 * @date :2020/9/23
 */
open class BaseDelegateAdapter(
    private val mContext: Context, layoutHelper: LayoutHelper, layoutId: Int, count: Int,
    viewTypeItem: Int
) : DelegateAdapter.Adapter<BaseViewHolder?>() {
    private val mLayoutHelper: LayoutHelper
    private var mCount = -1
    private var mLayoutId = -1
    private var mViewTypeItem = -1
    override fun onCreateLayoutHelper(): LayoutHelper {
        return mLayoutHelper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {}

    /**
     * 必须重写不然会出现滑动不流畅的情况
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return mViewTypeItem
    }

    //条目数量
    override fun getItemCount(): Int {
        return mCount
    }

    init {
        mCount = count
        mLayoutHelper = layoutHelper
        mLayoutId = layoutId
        mViewTypeItem = viewTypeItem
    }
}