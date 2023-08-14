package com.techsultan.bookxchange.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.bookxchange.MainActivity
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.adapter.GoogleBooksAdapter
import com.techsultan.bookxchange.databinding.FragmentSearchBinding
import com.techsultan.bookxchange.util.Resource
import com.techsultan.bookxchange.viewmodel.BookViewModel


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleBooksAdapter: GoogleBooksAdapter
    private lateinit var bookViewModel : BookViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {

        googleBooksAdapter = GoogleBooksAdapter()

        binding.googleBooksRv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = googleBooksAdapter

            bookViewModel.googleBooksResult().observe(viewLifecycleOwner) { books ->

                googleBooksAdapter.differ.currentList
            }
        }
    }

}