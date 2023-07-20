package com.techsultan.bookxchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.techsultan.bookxchange.databinding.FragmentBookDetailsBinding
import com.techsultan.bookxchange.model.Book


class BookDetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: BookDetailsFragmentArgs by navArgs()
    private lateinit var bookDetails : Book

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookDetails = args.bookDetails!!

        binding.tvDetailsBookName.text = bookDetails.bookName
        binding.authorName.text = bookDetails.author
        binding.tvBookDescription.text = bookDetails.description
        binding.tvNumberPage.text = bookDetails.noOfPages
        Glide.with(requireContext())
            .load(bookDetails.bookImage)
            .transform(RoundedCorners(7))
            .into(binding.imgBook)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}