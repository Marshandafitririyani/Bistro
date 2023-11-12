package com.maruchan.bistro.data.room.bistro


import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class BistroDetail(
    @SerializedName("address")
    val address: String?,
    @SerializedName("category")
    val category: List<String>?,
    @SerializedName("category_id")
    val categoryId: Int?,
    @SerializedName("city")
    val city: Int?,
    @SerializedName("city_id")
    val cityId: Int?,
    @SerializedName("contact")
    val contact: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("province")
    val province: Int?,
    @SerializedName("province_id")
    val provinceId: Int?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
):Parcelable{
    @Parcelize
    data class PhotoCarousel(
        @SerializedName("catalogue_id")
        val catalogueId: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("photo")
        val photo: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ):Parcelable
}