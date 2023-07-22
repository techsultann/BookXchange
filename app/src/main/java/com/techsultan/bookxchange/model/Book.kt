package com.techsultan.bookxchange.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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
    val userProfileImage: String? = null,

    ) : Parcelable