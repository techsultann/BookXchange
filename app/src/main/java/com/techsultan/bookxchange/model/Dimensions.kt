package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Dimensions(
    @SerializedName("height")
    val height: String?,
    @SerializedName("thickness")
    val thickness: String?,
    @SerializedName("width")
    val width: String?
) : Parcelable