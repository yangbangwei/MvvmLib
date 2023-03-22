package com.yangbw.libtest.api

import com.yangbw.libtest.common.BaseRes
import com.yangbw.libtest.entity.*
import retrofit2.http.*

/**
 * API接口
 *
 * @author yangbw
 * @date 2020-03-16.
 */
interface ApiService {

    @POST("api/loginByPhone")
    @FormUrlEncoded
    suspend fun loginByPhone(
        @Field("phone") phone: String
    ): BaseRes<UserData>

    @POST("api/sendCode")
    @FormUrlEncoded
    suspend fun sendCode(@Field("phone") phone: String? = null): BaseRes<String>

    @POST("api/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("phone") username: String, @Field("code") code: String
    ): BaseRes<UserData>

    @POST("api/verifyCode")
    @FormUrlEncoded
    suspend fun verifyCode(@Field("code") code: String): BaseRes<String>

    @POST("api/changePhone")
    @FormUrlEncoded
    suspend fun changePhone(
        @Field("phone") phone: String, @Field("code") code: String
    ): BaseRes<String>

    @GET("api/banners")
    suspend fun banners(): BaseRes<List<BannerInfo>>

    @GET("api/updateVersion")
    suspend fun updateVersion(@Query("versionCode") versonCode: Int): BaseRes<UpdateVersion>

    @GET("api/homeDetails")
    suspend fun homeDetails(@Query("id") id: String): BaseRes<DetailsData>

    @POST("api/userInfo")
    suspend fun userInfo(): BaseRes<MineData>

    @POST("api/discoverHot")
    @FormUrlEncoded
    suspend fun discoverHot(@Field("pageNo") pageNo: Int): BaseRes<List<HotListData>>

    @POST("api/discoverNew")
    @FormUrlEncoded
    suspend fun discoverNew(@Field("pageNo") pageNo: Int): BaseRes<List<NewestListData>>

    @POST("api/discoverRecommend")
    @FormUrlEncoded
    suspend fun discoverRecommend(@Field("pageNo") pageNo: Int): BaseRes<List<RecommendListData>>

    @POST("api/discoverRecommendLike")
    @FormUrlEncoded
    suspend fun discoverRecommendLike(@Field("id") id: String): BaseRes<String>

    @POST("api/menuList")
    @FormUrlEncoded
    suspend fun menuList(@Field("pageNo") pageNo: Int): BaseRes<List<MenuData>>

    @GET("api/couponList")
    suspend fun couponList(): BaseRes<List<CouponData>>

    @POST("api/changeName")
    @FormUrlEncoded
    suspend fun changeName(@Field("name") username: String): BaseRes<String>
}