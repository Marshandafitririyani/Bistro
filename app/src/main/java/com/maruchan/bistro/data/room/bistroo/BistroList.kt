package com.maruchan.bistro.data.room.bistroo


import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class BistroList(
    @SerializedName("address")
    val address: String?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("city")
    val city: City?,
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
    val province: Province?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?

): Parcelable