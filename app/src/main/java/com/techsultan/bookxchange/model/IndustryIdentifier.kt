package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class IndustryIdentifier(
    @SerializedName("identifier")
    val identifier: String?,
    @SerializedName("type")
    val type: String?
) : Parcelable