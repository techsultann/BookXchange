package com.techsultan.bookxchange.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techsultan.bookxchange.model.Chat
import com.techsultan.bookxchange.repository.ChatRepository


class ChatViewModel(
    private val repository : ChatRepository
) : ViewModel() {


    fun sendMessageResult(message: String, receiverId: String
                            ) = repository.sendMessage(message, receiverId)
    fun recieveMessageResult() = repository.receivedMessage()
}

class ChatViewModelFactory(
    private val repository: ChatRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(repository) as T
    }
}