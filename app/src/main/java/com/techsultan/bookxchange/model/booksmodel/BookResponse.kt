package com.techsultan.bookxchange.model.booksmodel


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import kotlinx.parcelize.RawValue

@Parcelize
data class BookResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: @RawValue Any?,
    @SerializedName("results")
    val results: List<Result>
) : Parcelable