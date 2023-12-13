package com.techsultan.bookxchange.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techsultan.bookxchange.databinding.ChatListItemBinding
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.model.Chat

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ChatListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Chat>() {

        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.ChatViewHolder {
        return ChatViewHolder(ChatListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ChatListAdapter.ChatViewHolder, position: Int) {
        val chat = differ.currentList[position]

        holder.binding.apply {

            nameTv.text = chat.name
            chatTv.text = chat.message

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}