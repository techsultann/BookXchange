package com.techsultan.bookxchange.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.databinding.BooksItemBinding
import com.techsultan.bookxchange.fragments.home.HomeFragmentDirections
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.model.Chat

class BookAdapter() : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: BooksItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Book>() {

        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookId== newItem.bookId
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(BooksItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = differ.currentList[position]

        holder.binding.apply {

            tvPersonName.text = book.userName.toString()
            tvAuthorName.text = book.author.toString()
            tvBookName.text = book.bookName.toString()
            tvBuyOrSwap.text = book.actionText.toString()
            swapOrBuyBtn.text = book.category.toString()

            Glide.with(holder.itemView)
                .load(book.bookImage)
                .transform(RoundedCorners(7))
                .into(bookCoverImg)
            Glide.with(holder.itemView)
                .load(book.userProfileImage)
                .into(profileImage)

        }
        if (book.ownerUid != Firebase.auth.currentUser?.uid) {

            holder.binding.swapOrBuyBtn.setOnClickListener {
                val directions = HomeFragmentDirections.actionHomeDestToChatDest2(book)
                Log.d("UserId", "Book UserId: ${book.ownerUid}")
                it.findNavController().navigate(directions)
            }

        } else {

            return
        }



        holder.itemView.setOnClickListener { mView ->
             val directions = HomeFragmentDirections.actionHomeFragmentToDetailsBookFragment(book)
            mView.findNavController().navigate(directions)
        }

    }

    fun deletePost() {
        val userId = Firebase.auth.currentUser?.uid
        val db = Firebase.firestore

        if (userId != null) {
            db.collection("users").document(userId)
                .collection("books")
                .document("")
        }
    }

}