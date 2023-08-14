package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SaleInfo(
    @SerializedName("country")
    val country: String?,
    @SerializedName("isEbook")
    val isEbook: Boolean?,
    @SerializedName("saleability")
    val saleability: String?
) : Parcelable