package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Pdf(
    @SerializedName("isAvailable")
    val isAvailable: Boolean?
) : Parcelable