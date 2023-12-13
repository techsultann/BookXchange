package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class VolumeInfo(
    @SerializedName("authors")
    val authors: List<String?>?,
    @SerializedName("averageRating")
    val averageRating: Double?,
    @SerializedName("canonicalVolumeLink")
    val canonicalVolumeLink: String?,
    @SerializedName("categories")
    val categories: List<String?>?,
    @SerializedName("contentVersion")
    val contentVersion: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dimensions")
    val dimensions: Dimensions?,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?,
    @SerializedName("industryIdentifiers")
    val industryIdentifiers: List<IndustryIdentifier?>?,
    @SerializedName("infoLink")
    val infoLink: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("mainCategory")
    val mainCategory: String?,
    @SerializedName("pageCount")
    val pageCount: Int?,
    @SerializedName("printType")
    val printType: String?,
    @SerializedName("publishedDate")
    val publishedDate: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("ratingsCount")
    val ratingsCount: Int?,
    @SerializedName("title")
    val title: String?
) : Parcelable