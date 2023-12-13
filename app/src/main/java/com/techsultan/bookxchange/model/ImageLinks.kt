package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ImageLinks(
    @SerializedName("extraLarge")
    val extraLarge: String?,
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("smallThumbnail")
    val smallThumbnail: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
) : Parcelable