package com.yangbw.libtest.ui.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.library.common.utils.GlideUtils

/**
 * @author yangbw
 */
object ImageViewAdapter {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, bitmap: Bitmap?) {
        view.setImageBitmap(bitmap)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            GlideUtils.loadImage(imageView, it)
        }
    }

    @JvmStatic
    @BindingAdapter("loadCircleImage")
    fun loadCircleImage(imageView: ImageView, url: String?) {
        url?.let {
            GlideUtils.loadCircleImage(imageView, it)
        }
    }

    @JvmStatic
    @BindingAdapter("loadRoundImage")
    fun loadRoundImage(imageView: ImageView, url: String?) {
        url?.let {
            GlideUtils.loadRoundImage(imageView, it)
        }

    }

    @JvmStatic
    @BindingAdapter("imageUrl", "placeHolder", "error")
    fun loadImage(
        imageView: ImageView,
        url: String?,
        holderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(holderDrawable)
            .error(errorDrawable)
            .into(imageView)
    }
}