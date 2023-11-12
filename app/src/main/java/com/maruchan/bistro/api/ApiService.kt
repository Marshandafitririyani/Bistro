package com.maruchan.bistro.api

import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("phone_or_email") phoneOrEmail: String?,
        @Field("password") password: String?
    ): String

    @FormUrlEncoded
    @POST("api/register")
    suspend fun register(
        @Field("name") name: String?,
        @Field("phone_or_email") phoneOrEmail: String?,
        @Field("password") password: String?,
        @Field("confirm_password") confirmPassword: String?
    ): String

    @FormUrlEncoded
    @POST("api/user/edit-profile")
    suspend fun updateProfil(
        @Field("name") name: String?,
    ): String

    @Multipart
    @POST("api/user/edit-profile")
    suspend fun updateProfileWithPhoto(
        @Query("name") name: String?,
        @Part photo: MultipartBody.Part?
    ): String

    @FormUrlEncoded
    @POST("api/user/edit-password")
    suspend fun editPassword(
        @Field("old_password") oldPassword: String?,
        @Field("new_password") newPassword: String?,
        @Field("confirm_new_password") confirmNewPassword: String?
    ): String

    @GET("api/user/profile")
    suspend fun getProfile(): String

    @GET("api/resto/list")
    suspend fun getBistroList(
    ): String

    @GET("api/auth/getcarts")
    suspend fun getCart(
    ): String

    @POST("api/logout")
    suspend fun logout(): String

    @GET("api/auth/refresh")
    suspend fun getRenewToken(): String

    @GET("api/auth/img_slider")
    suspend fun slider(
    ): String

    @POST("api/cart/add")
    suspend fun addCart(
        @Path("id") id: Int?
    ): String

    @GET("api/list-category")
    suspend fun getCategory(): String

    @GET("api/resto/detail/1")
    suspend fun bistroCategory(): String

    @GET("api/resto/detail/{id}")
    suspend fun bistroCategory(
        @Path("id")id : String?
    ) : String


    @POST("api/user/save-resto")
    suspend fun liked(
        @Field("resto_id") restoId: Int?,
    ): String

    @POST("api/user/saved-resto")
    suspend fun unLiked(
        @Field("resto_id") restoId: Int?,
    ): String
}