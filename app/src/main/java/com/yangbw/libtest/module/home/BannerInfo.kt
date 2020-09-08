package com.yangbw.libtest.module.home


/**
 * @author :yangbw
 * @date :2020/8/7
 */
class BannerInfo(
    var type: Int,
    var datas: MutableList<Data>
) {

    class Data(
        var id: String,
        var img: String,
        var title: String,
        var url: String
    )
}
