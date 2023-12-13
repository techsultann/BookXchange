package com.techsultan.bookxchange.model

import android.net.Uri
import java.sql.Timestamp
import java.time.LocalDateTime

data class Chat(
    val messageId: String? = null,
    val senderId: String? = null,
    val receiverId: String? = null,
    val name: String? = null,
    val message: String,
    val timestamp: Long? = null
)
