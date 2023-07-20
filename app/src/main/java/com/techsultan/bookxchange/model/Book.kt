package com.techsultan.bookxchange.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
data class Book(
    val id: String? = null,
    val bookName: String? = null,
    val description: String? = null,
    val author: String? = null,
    val bookImage: String? = null,
    val userName: String? = null,
    val noOfPages: String? = null,
    val category: String? = null,
    val actionText: String? = null,

) : Parcelable