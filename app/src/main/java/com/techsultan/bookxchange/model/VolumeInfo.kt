package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class VolumeInfo(
    @SerializedName("allowAnonLogging")
    val allowAnonLogging: Boolean?,
    @SerializedName("authors")
    val authors: List<String?>?,
    @SerializedName("averageRating")
    val averageRating: Int?,
    @SerializedName("canonicalVolumeLink")
    val canonicalVolumeLink: String?,
    @SerializedName("contentVersion")
    val contentVersion: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dimensions")
    val dimensions: Dimensions?,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?,
    @SerializedName("infoLink")
    val infoLink: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("maturityRating")
    val maturityRating: String?,
    @SerializedName("pageCount")
    val pageCount: Int?,
    @SerializedName("panelizationSummary")
    val panelizationSummary: PanelizationSummary?,
    @SerializedName("previewLink")
    val previewLink: String?,
    @SerializedName("printType")
    val printType: String?,
    @SerializedName("printedPageCount")
    val printedPageCount: Int?,
    @SerializedName("publishedDate")
    val publishedDate: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("ratingsCount")
    val ratingsCount: Int?,
    @SerializedName("readingModes")
    val readingModes: ReadingModes?,
    @SerializedName("title")
    val title: String?
) : Parcelable