package com.yangbw.libtest.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.library.common.utils.GlideUtils
import com.yangbw.libtest.entity.BannerInfo
import com.yangbw.libtest.ui.adapter.MineAdapter.BannerViewHolder
import com.youth.banner.adapter.BannerAdapter

class MineAdapter(mDatas: List<BannerInfo.Data>) : BannerAdapter<BannerInfo.Data, BannerViewHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType=ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

    override fun onBindView(holder: BannerViewHolder, data: BannerInfo.Data, position: Int, size: Int) {
        //图片加载自己实现
        GlideUtils.loadImage(holder.imageView, data.img)
    }
}