package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ListPrice(
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("currencyCode")
    val currencyCode: String?
) : Parcelable