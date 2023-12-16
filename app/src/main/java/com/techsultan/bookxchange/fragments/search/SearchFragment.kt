package com.techsultan.bookxchange.fragments.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.bookxchange.adapter.BookAdapter
import com.techsultan.bookxchange.adapter.GoogleBooksAdapter
import com.techsultan.bookxchange.databinding.FragmentSearchBinding
import com.techsultan.bookxchange.repository.SearchRepository
import com.techsultan.bookxchange.viewmodel.SearchViewModel
import com.techsultan.bookxchange.viewmodel.SearchViewModelFactory


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

        val searchBar = binding.searchBar
        val searchView = binding.searchView

        suggestedBooksRecyclerView()
        searchResultRecyclerView()

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

//        searchBar.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                // Show search suggestions or trigger animation
//                val query = searchBar.text.toString()
//                searchViewModel.searchBooks(query)
//            } else {
//                // Clear search query or perform other actions
//                searchBar.clearText()
//            }
//        }

        //TODO: you can also use TextChangeListener to get the text from the searchView in realtime,
        // and filter your search, which is equivalent to the onQueryListener.
        searchView
            .editText
            .addTextChangedListener (
                beforeTextChanged = { text, start, count, after ->

                },
                onTextChanged = { text, start, before, count ->
//                    searchViewModel.searchBooks(text.toString())
                },
                afterTextChanged = {

                }
            )

       //TODO: This will be triggered when you click on search from your phone touch keyboard
        searchView
            .editText
            .setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
                searchBar.text = searchView.text
                searchView.hide()
                //TODO: add your query search here, your query method itself from the
                // searchViewModel.searchBooks() is not working, fix it.
                //TODO: tested it, with searchViewModel.fetchBooks(), and i got a list after my search
                //searchViewModel.fetchBooks()
                searchViewModel.searchBooks(searchView.text.toString())
                false
            }

//        searchViewModel.fetchBooks()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun suggestedBooksRecyclerView() {

        googleBooksAdapter = GoogleBooksAdapter()

        binding.booksRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,
                false)
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