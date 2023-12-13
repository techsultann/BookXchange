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
import com.techsultan.bookxchange.model.booksmodel.Result

class GoogleBooksAdapter : RecyclerView.Adapter<GoogleBooksAdapter.GoogleBooksViewHolder>() {

    inner class GoogleBooksViewHolder(
        val binding: GoogleBooksItemBinding
        ) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
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
        val result = differ.currentList[position]
        holder.binding.apply {

            Glide.with(holder.itemView)
                .load(result.formats.imagejpeg)
                .fitCenter()
                .transform(RoundedCorners(15))
                .into(bookImage)
            tvBookName.text = result.title

        }
    }


}