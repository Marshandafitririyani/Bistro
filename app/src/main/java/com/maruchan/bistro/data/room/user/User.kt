package com.maruchan.bistro.data.room.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class User(
    @PrimaryKey
    @Expose
    @SerializedName("id_room")
    val idRoom: Int,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_or_email")
    val phoneOrEmail: String?,
    @SerializedName("photo_profile")
    val photoProfile: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable