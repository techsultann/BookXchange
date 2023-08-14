package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GoogleBooksResponse(
    @SerializedName("accessInfo")
    val accessInfo: AccessInfo?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("layerInfo")
    val layerInfo: LayerInfo?,
    @SerializedName("saleInfo")
    val saleInfo: SaleInfo?,
    @SerializedName("selfLink")
    val selfLink: String?,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo?
) : Parcelable