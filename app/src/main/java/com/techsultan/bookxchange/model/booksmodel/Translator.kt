package com.techsultan.bookxchange.model.booksmodel


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import kotlinx.parcelize.RawValue

@Parcelize
data class Translator(
    @SerializedName("birth_year")
    val birthYear: @RawValue Any?,
    @SerializedName("death_year")
    val deathYear: @RawValue Any?,
    @SerializedName("name")
    val name: String
) : Parcelable