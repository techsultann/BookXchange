package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AccessInfo(
    @SerializedName("accessViewStatus")
    val accessViewStatus: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("embeddable")
    val embeddable: Boolean?,
    @SerializedName("epub")
    val epub: Epub?,
    @SerializedName("pdf")
    val pdf: Pdf?,
    @SerializedName("publicDomain")
    val publicDomain: Boolean?,
    @SerializedName("textToSpeechPermission")
    val textToSpeechPermission: String?,
    @SerializedName("viewability")
    val viewability: String?
) : Parcelable