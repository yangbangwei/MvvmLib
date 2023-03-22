package com.yangbw.libtest.entity

import androidx.annotation.Keep

/**
 * @author yangbw
 * @date
 */
@Keep
data class DetailsData(
    val content: String,
    val id: String,
    val img: String,
    val title: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetailsData

        if (content != other.content) return false
        if (id != other.id) return false
        if (img != other.img) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + img.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }

}
