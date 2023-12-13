package com.techsultan.bookxchange.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SaleInfo(
    @SerializedName("buyLink")
    val buyLink: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("isEbook")
    val isEbook: Boolean?,
    @SerializedName("listPrice")
    val listPrice: ListPrice?,
    @SerializedName("retailPrice")
    val retailPrice: RetailPrice?,
    @SerializedName("saleability")
    val saleability: String?
) : Parcelable