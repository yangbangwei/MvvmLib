package com.library.common.view.baseviewholder

import android.view.View
import androidx.annotation.IntDef
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * CommonViewHolder
 *
 * @author yangbw
 * @date 2020/8/31
 */
class CommonViewHolder(view: View) : BaseViewHolder(view) {
    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    internal annotation class Visibility

    fun setVisibility(viewId: Int, @Visibility visibility: Int): CommonViewHolder {
        val view: View = getView(viewId)
        view.visibility = visibility
        return this
    }


}