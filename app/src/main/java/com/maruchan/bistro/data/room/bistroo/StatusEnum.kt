package com.maruchan.bistro.data.room.bistroo

import com.google.gson.annotations.SerializedName

enum class StatusEnum {
    @SerializedName("Belum melakukan absensi")
    BELUM_ABSEN,

    @SerializedName("Bakery")
    BAKERY,

    @SerializedName("Keluar")
    KELUAR,

    @SerializedName("Izin")
    IZIN,

    @SerializedName("Tidak Masuk")
    TIDAK_MASUK
}