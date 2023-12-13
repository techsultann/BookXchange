package com.techsultan.bookxchange.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.databinding.ReceivedMessageLayoutBinding
import com.techsultan.bookxchange.databinding.SentMessageLayoutBinding
import com.techsultan.bookxchange.model.Chat

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SenderViewHolder(val binding : SentMessageLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ReceiverViewHolder(val binding : ReceivedMessageLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }

    private val differCallback = object : DiffUtil.ItemCallback<Chat>() {

        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SENDER -> {
                val sendMessageLayout = SentMessageLayoutBinding.inflate(inflater, parent, false)
                SenderViewHolder(sendMessageLayout)
            }
            else -> {
                val receiveMessageLayout = ReceivedMessageLayoutBinding.inflate(inflater, parent, false)
                ReceiverViewHolder(receiveMessageLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = differ.currentList[position]
        when (holder) {
            is SenderViewHolder ->  {

                holder.binding.apply {
                    sentMsgTv.text = chat.message
                }

            }
            is ReceiverViewHolder -> {
                holder.binding.apply {
                    sentMsgTv.text = chat.message

                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = differ.currentList[position]

        val currentUserId = Firebase.auth.currentUser?.uid

        return if (chat.senderId == currentUserId) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_RECEIVER
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}