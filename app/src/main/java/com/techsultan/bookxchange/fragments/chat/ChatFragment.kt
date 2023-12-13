package com.techsultan.bookxchange.fragments.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.bookxchange.adapter.ChatAdapter
import com.techsultan.bookxchange.databinding.FragmentChatBinding
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.repository.ChatRepository
import com.techsultan.bookxchange.viewmodel.ChatViewModel
import com.techsultan.bookxchange.viewmodel.ChatViewModelFactory


class ChatFragment : Fragment() {
    private var _binding : FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatadapter: ChatAdapter
    private lateinit var chatViewModel: ChatViewModel
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var bookDetails: Book


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ChatRepository()
        val viewModelFactory = ChatViewModelFactory(repository)
        chatViewModel = ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java]

        setupRecyclerView()

        bookDetails = args.details!!
        val receiverId = bookDetails.ownerUid!!

        binding.sendBtn.setOnClickListener {
            val messageText = binding.messageEt.text.toString()
            if (messageText.isNotEmpty()) {
                binding.sendBtn.visibility = View.VISIBLE
                chatViewModel.sendMessageResult(messageText, receiverId)
                Log.d("message", "Message sent: $messageText")
                binding.messageEt.text?.clear()
            }
        }
    }


    private fun setupRecyclerView() {

        chatadapter = ChatAdapter()

        binding.chatRv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = chatadapter

            chatViewModel.recieveMessageResult().observe(viewLifecycleOwner) { receivedMessage ->

                chatadapter.differ.submitList(receivedMessage)
                // This scrolls to the latest message
                Log.d("Chat list", "Messages: $receivedMessage")
                smoothScrollToPosition(chatadapter.itemCount -1)
            }
        }
    }

    /*private fun sendMessage() {
        binding.sendBtn.visibility = View.GONE
        val messageText = binding.messageEt.text.toString().trim()

        if (messageText.isNotEmpty()) {

            binding.sendBtn.visibility = View.VISIBLE
            chatViewModel.sendMessageResult()
            Log.d("message", "Message sent")
            binding.messageEt.text?.clear()
        }
    }*/

}