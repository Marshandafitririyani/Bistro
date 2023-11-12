package com.maruchan.bistro.data.room.bistroo


import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Category(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
): Parcelable  {
    override fun toString(): String {
        return name.toString()
    }
}