package com.yangbw.libtest.ui.adapter

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.library.common.base.BaseAdapter
import com.library.common.extension.gone
import com.library.common.extension.visible
import com.library.common.view.baseviewholder.CommonViewHolder
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.ItemActivityMenuBinding
import com.yangbw.libtest.databinding.ItemActivityMenuPicBinding
import com.yangbw.libtest.entity.MenuData

/**
 * @author yangbw
 * @date
 */
class MenuAdapter : BaseAdapter<MenuData>(R.layout.item_activity_menu, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemActivityMenuBinding>(viewHolder.itemView)
    }

    override fun convert(helper: CommonViewHolder, item: MenuData) {
        val itemListBinding = helper.getBinding<ItemActivityMenuBinding>()
        if (itemListBinding != null) {
            itemListBinding.run {
                this.item = item
                ivClose.setOnClickListener {
                    val activity = context as Activity
                    activity.finish()
                }
            }

            when (item.type) {
                IMAGE -> {
                    itemListBinding.run {
                        videoPlayer.gone()
                        viewPager.visible()
                        tvNum.visible()
                        val adapter = PhotosAdapter()
                        adapter.addData(item.picUrls)
                        viewPager.adapter = adapter
                        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        viewPager.offscreenPageLimit = 1
                        tvNum.text = String.format(
                            context.getString(R.string.photo_count),
                            1,
                            item.picUrls.size
                        )
                        viewPager.registerOnPageChangeCallback(object :
                            ViewPager2.OnPageChangeCallback() {
                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)
                                tvNum.text = String.format(
                                    context.getString(R.string.photo_count),
                                    position + 1,
                                    item.picUrls.size
                                )
                            }
                        })
                    }
                }
                VIDEO -> {
                    itemListBinding.run {
                        videoPlayer.visible()
                        viewPager.gone()
                        tvNum.gone()

                        videoPlayer.run {
                            val cover = ImageView(context)
                            Glide.with(cover.context)
                                .setDefaultRequestOptions(
                                    RequestOptions()
                                        .frame(1000000)
                                )
                                .load(item.videoUrl)
                                .into(cover)
                            cover.parent?.run { removeView(cover) }
                            thumbImageView = cover
                            setThumbPlay(true)
                            setIsTouchWiget(false)
                            isLooping = true
                            playTag = TAG
                            playPosition = helper.adapterPosition
                            setUp(item.videoUrl, false, null)
                        }
                    }
                }
            }
        }
    }

    inner class PhotosAdapter : BaseAdapter<String>(R.layout.item_activity_menu_pic, ArrayList()) {

        override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
            DataBindingUtil.bind<ItemActivityMenuPicBinding>(viewHolder.itemView)
        }

        override fun convert(helper: CommonViewHolder, item: String) {
            val itemListBinding = helper.getBinding<ItemActivityMenuPicBinding>()
            if (itemListBinding != null) {
                Glide.with(context).load(item).into(itemListBinding.ivPic)
            }
        }
    }

    companion object {
        const val TAG = "MenuAdapter"
        const val IMAGE = "1"
        const val VIDEO = "2"
    }

}
