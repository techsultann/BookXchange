package com.techsultan.bookxchange.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.bookxchange.MainActivity
import com.techsultan.bookxchange.adapter.GoogleBooksAdapter
import com.techsultan.bookxchange.databinding.FragmentSearchBinding
import com.techsultan.bookxchange.viewmodel.BookViewModel


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleBooksAdapter: GoogleBooksAdapter
    private lateinit var bookViewModel : BookViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel = (activity as MainActivity).viewModel

        val searchBar = binding.searchBar
        val searchView = binding.searchView

        searchView.setupWithSearchBar(searchBar)
        searchBar.hint = "What are you looking for?"

        searchBar.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    // Show search suggestions or trigger animation
                } else {
                    // Clear search query or perform other actions
                }
            }

        }

        setupRecyclerView()
        bookViewModel.fetchBooks()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {

        googleBooksAdapter = GoogleBooksAdapter()

        binding.booksRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = googleBooksAdapter

           bookViewModel.books.observe(viewLifecycleOwner) { books ->
               googleBooksAdapter.differ.submitList(books)

            }
        }
    }

}