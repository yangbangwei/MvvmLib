package com.library.common.utils

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.library.common.config.AppConfig
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * glide图片加载
 *
 * @author yangbw
 * @date 2020/9/1
 */
@Suppress("unused")
class GlideUtils {

    companion object {

        private val options = RequestOptions()
            .centerCrop()
            .placeholder(AppConfig.getPlaceholder())
            .error(AppConfig.getErrorImage())
            .skipMemoryCache(AppConfig.isSkipMemoryCache())

        /**
         * 加载图片(String地址)
         */
        fun loadImage(imageView: ImageView, url: String?) {
            Glide.with(imageView.context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载图片(int资源地址)
         */
        fun loadImage(imageView: ImageView, res: Int) {
            Glide.with(imageView.context).load(res).apply(options).into(imageView)
        }

        /**
         * 加载图片(uri)
         */
        fun loadImage(imageView: ImageView, uri: Uri) {
            Glide.with(imageView.context).load(uri).apply(options).into(imageView)
        }

        /**
         * 加载图片(bitmap)
         */
        fun loadImage(imageView: ImageView, bitmap: Bitmap) {
            Glide.with(imageView.context).load(bitmap).apply(options).into(imageView)
        }

        /**
         * 加载图片(String地址)---指定宽高
         */
        fun loadImage(
            imageView: ImageView,
            url: String,
            width: Int,
            height: Int
        ) {
            val options = RequestOptions()
                .centerCrop()
                .placeholder(AppConfig.getPlaceholder())
                .error(AppConfig.getErrorImage())
                .override(width, height)
                .skipMemoryCache(AppConfig.isSkipMemoryCache())
            Glide.with(imageView.context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆形图片
         */
        fun loadCircleImage(imageView: ImageView, url: String?) {
            val options = RequestOptions()
                .circleCrop()
                .placeholder(AppConfig.getPlaceholder())
                .error(AppConfig.getErrorImage())
                .skipMemoryCache(AppConfig.isSkipMemoryCache())
            Glide.with(imageView.context).load(url).apply(options).into(imageView)

        }

        /**
         * 加载圆形图片
         */
        fun loadRoundImage(imageView: ImageView, url: String?) {
            val options = RequestOptions()
                .circleCrop()
                .placeholder(AppConfig.getPlaceholder())
                .error(AppConfig.getErrorImage())
                .transform(
                    RoundedCornersTransformation(
                        15,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
                .skipMemoryCache(AppConfig.isSkipMemoryCache())
            Glide.with(imageView.context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆形图片---指定圆角半径
         */
        fun loadRoundImage(imageView: ImageView, url: String, radius: Int) {
            val options = RequestOptions()
                .circleCrop()
                .placeholder(AppConfig.getPlaceholder())
                .error(AppConfig.getErrorImage())
                .transform(
                    RoundedCornersTransformation(
                        radius,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
                .skipMemoryCache(AppConfig.isSkipMemoryCache())
            Glide.with(imageView.context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）和半径
         */
        fun loadCustomRoundImage(
            imageView: ImageView,
            url: String,
            type: RoundedCornersTransformation.CornerType,
            radius: Int
        ) {
            val options = RequestOptions()
                .circleCrop()
                .placeholder(AppConfig.getPlaceholder())
                .error(AppConfig.getErrorImage())
                .transform(RoundedCornersTransformation(radius, 0, type))
                .skipMemoryCache(AppConfig.isSkipMemoryCache())
            Glide.with(imageView.context).load(url).apply(options).into(imageView)
        }

    }
}