package com.techsultan.bookxchange.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchView
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.adapter.BookAdapter
import com.techsultan.bookxchange.adapter.GoogleBooksAdapter
import com.techsultan.bookxchange.databinding.FragmentSearchBinding
import com.techsultan.bookxchange.repository.ChatRepository
import com.techsultan.bookxchange.repository.SearchRepository
import com.techsultan.bookxchange.viewmodel.ChatViewModel
import com.techsultan.bookxchange.viewmodel.ChatViewModelFactory
import com.techsultan.bookxchange.viewmodel.SearchViewModel
import com.techsultan.bookxchange.viewmodel.SearchViewModelFactory
import materialsearchview.MaterialSearchView


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleBooksAdapter: GoogleBooksAdapter
    private lateinit var searchBookAdapter: BookAdapter
    private lateinit var searchViewModel : SearchViewModel


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


        val repository = SearchRepository()
        val viewModelFactory = SearchViewModelFactory(repository)
        searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

        suggestedBooksRecyclerView()
        searchResultRecyclerView()
        searchViewModel.fetchBooks()

        val searchBar = binding.searchBar
        val searchView = binding.searchView

        searchView.setupWithSearchBar(searchBar)

        /*searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // This method is called when the user submits the query
                    searchViewModel.searchBooks(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // This method is called when the text in the search view changes
                // You can use it to update search suggestions or perform other actions
                return true
            }
        })*/

        searchBar.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // Show search suggestions or trigger animation
                val query = searchBar.text.toString()
                searchViewModel.searchBooks(query)
            } else {
                // Clear search query or perform other actions
                searchBar.clearText()
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun suggestedBooksRecyclerView() {

        googleBooksAdapter = GoogleBooksAdapter()

        binding.booksRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = googleBooksAdapter

           searchViewModel.books.observe(viewLifecycleOwner) { books ->
               googleBooksAdapter.differ.submitList(books)

            }
        }
    }
    private fun searchResultRecyclerView() {

        searchBookAdapter = BookAdapter()
        binding.searchResultRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchBookAdapter

           searchViewModel.searchBooks.observe(viewLifecycleOwner) { searchResult ->
               searchBookAdapter.differ.submitList(searchResult)
            }
        }
    }


}