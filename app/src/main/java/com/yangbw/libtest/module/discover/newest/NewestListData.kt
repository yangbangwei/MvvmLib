package com.yangbw.libtest.module.discover.newest

import androidx.annotation.Keep
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author yangbw
 * @date
 */
@Keep
data class NewestListData(
    val avatar: Any,
    val img: Any,
    val name: Any,
    val title: Any,
    val url: Any,
    var type: Int = 0,
    val datas: List<Data>
) : MultiItemEntity {

    data class Data(
        val id: String,
        val img: String,
        val url: String
    )

    override val itemType: Int
        get() = type

    companion object {
        //模拟两种布局
        const val TYPE_HEAD = 0
        const val TYPE_BODY = 1
    }

}
