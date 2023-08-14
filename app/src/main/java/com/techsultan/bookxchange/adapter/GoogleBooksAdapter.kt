package com.techsultan.bookxchange.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.GoogleBooksItemBinding
import com.techsultan.bookxchange.model.GoogleBooksResponse
import com.techsultan.bookxchange.model.VolumeInfo

class GoogleBooksAdapter : RecyclerView.Adapter<GoogleBooksAdapter.GoogleBooksViewHolder>() {

    inner class GoogleBooksViewHolder(
        val binding: GoogleBooksItemBinding
        ) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<GoogleBooksResponse>() {
        override fun areItemsTheSame(
            oldItem: GoogleBooksResponse,
            newItem: GoogleBooksResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GoogleBooksResponse,
            newItem: GoogleBooksResponse
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoogleBooksViewHolder {
        return GoogleBooksViewHolder(GoogleBooksItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: GoogleBooksViewHolder, position: Int) {
        val googleBooks = differ.currentList[position]
        holder.binding.apply {

            tvAuthor.text = googleBooks.volumeInfo!!.authors.toString()
            tvBookName.text = googleBooks.volumeInfo.title

            Glide.with(holder.itemView)
                .load(googleBooks.volumeInfo.imageLinks!!.thumbnail)
                .placeholder(R.drawable.baseline_book)
                .fitCenter()
                .transform(RoundedCorners(10))
                .into(bookImage)
        }
    }


}