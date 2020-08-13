package com.qianxinde.libtest.module.home

import com.stx.xhb.androidx.entity.SimpleBannerInfo

/**
 * @author :yangbw
 * @date :2020/8/7
 */
class Banner(
    var img: String,
    var type: String,
    var url: String
) : SimpleBannerInfo() {
    override fun getXBannerUrl(): Any {
        return img
    }
}