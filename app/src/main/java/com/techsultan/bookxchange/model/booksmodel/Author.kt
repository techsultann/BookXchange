package com.techsultan.bookxchange.model.booksmodel


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Author(
    @SerializedName("birth_year")
    val birthYear: Int?,
    @SerializedName("death_year")
    val deathYear: Int?,
    @SerializedName("name")
    val name: String
) : Parcelable