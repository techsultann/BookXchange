package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ReadingModes(
    @SerializedName("image")
    val image: Boolean?,
    @SerializedName("text")
    val text: Boolean?
) : Parcelable