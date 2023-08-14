package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Layer(
    @SerializedName("layerId")
    val layerId: String?,
    @SerializedName("volumeAnnotationsVersion")
    val volumeAnnotationsVersion: String?
) : Parcelable