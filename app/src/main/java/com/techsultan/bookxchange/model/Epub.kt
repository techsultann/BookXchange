package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Epub(
    @SerializedName("acsTokenLink")
    val acsTokenLink: String?,
    @SerializedName("isAvailable")
    val isAvailable: Boolean?
) : Parcelable