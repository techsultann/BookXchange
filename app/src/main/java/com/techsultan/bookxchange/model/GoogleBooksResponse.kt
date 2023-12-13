package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GoogleBooksResponse(
    @SerializedName("saleInfo")
    val saleInfo: SaleInfo? = null,
    @SerializedName("selfLink")
    val selfLink: String? = null,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo?
) : Parcelable