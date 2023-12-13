package com.techsultan.bookxchange.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.model.Chat
import java.sql.Time
import java.sql.Timestamp
import java.util.Calendar

class ChatRepository {

    private val chatLiveData = MutableLiveData<List<Chat>>()
    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth


    fun sendMessage(message: String, receiverId: String) : LiveData<List<Chat>> {

        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        if (currentUser != null) {

            val chatData = Chat(
                senderId = userId,
                message = message,
                receiverId = receiverId,
                timestamp = Calendar.getInstance().timeInMillis
            )

            fireStore.collection("users").document(userId!!).collection("chat")
                .add(chatData)
            Log.d("Message", "Chat sent successfully: $chatData")
        }


        return chatLiveData
    }

    fun receivedMessage() : LiveData<List<Chat>> {
        val chatCollection = fireStore.collection("chat")

        chatCollection.orderBy("timestamp").get()
            .addOnSuccessListener { querySnapShot ->

                val messages = mutableListOf<Chat>()
                for (message in querySnapShot) {
                    val chat = message.toObject(Chat::class.java)
                    messages.add(chat)
                }
                Log.d("Message list", "Chat List: $messages")
            }

        return chatLiveData
    }
}