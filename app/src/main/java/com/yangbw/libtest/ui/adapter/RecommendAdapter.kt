package com.yangbw.libtest.ui.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.extension.dp2px
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemFragmentRecommendBinding
import com.yangbw.libtest.entity.RecommendListData

/**
 * @author yangbw
 * @date
 */
@Suppress("DEPRECATION")
class RecommendAdapter :
    BaseAdapter<RecommendListData>(R.layout.item_fragment_recommend, ArrayList()) {

    /**
     * 通过获取屏幕宽度来计算出每张图片最大的宽度。
     *
     * @return 计算后得出的每张图片最大的宽度。
     */
    private val maxImageWidth: Int
        get() {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay?.getMetrics(metrics)
            val columnWidth = metrics.widthPixels
            return (columnWidth - ((14 * 2) + (dp2px(3f) * 2))) / 2
        }

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemFragmentRecommendBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: RecommendListData) {
        val itemListBinding = helper.getBinding<ItemFragmentRecommendBinding>()
        if (itemListBinding != null) {
            val imageHeight = calculateImageHeight(item.width, item.height)
            itemListBinding.item = item
            itemListBinding.ivImg.run {
                layoutParams.width = maxImageWidth
                layoutParams.height = imageHeight
            }
        }
    }

    /**
     * 根据屏幕比例计算图片高
     *
     * @param originalWidth   服务器图片原始尺寸：宽
     * @param originalHeight  服务器图片原始尺寸：高
     * @return 根据比例缩放后的图片高
     */
    private fun calculateImageHeight(originalWidth: Int, originalHeight: Int): Int {
        //服务器数据异常处理
        if (originalWidth == 0 || originalHeight == 0) {
            return maxImageWidth
        }
        return maxImageWidth * originalHeight / originalWidth
    }
}
