package com.library.common.mvvm

/**
 * 列表刷新处理
 *
 * @author yangbw
 * @date 2020/8/31
 */
interface IListView<T> : IView {
    /**
     * 刷新结束
     */
    fun refreshComplete()

    /**
     * 显示数据
     * @param datas
     * @param pageNum
     */
    fun showListData(datas: List<T>?, pageNum: Int)
}