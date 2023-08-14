package com.techsultan.bookxchange.fragments.profile

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.MainActivity
import com.techsultan.bookxchange.adapter.BookAdapter
import com.techsultan.bookxchange.databinding.FragmentMyBooksBinding
import com.techsultan.bookxchange.repository.BookRepository
import com.techsultan.bookxchange.viewmodel.BookMainViewModelFactory
import com.techsultan.bookxchange.viewmodel.BookViewModel


class MyBooksFragment : Fragment() {
    private var _binding : FragmentMyBooksBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookAdapter: BookAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)

        val repository = BookRepository(requireContext())
        val application = Application()
        val viewModelFactory = BookMainViewModelFactory(application, repository)
        bookViewModel = ViewModelProvider(this, viewModelFactory)[BookViewModel::class.java]

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        setupRecyclerView()

       // bookViewModel = (activity as MainActivity).viewModel


    }


    private fun setupRecyclerView() {
        bookAdapter = BookAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = bookAdapter

            bookViewModel.myBooksResult().observe(viewLifecycleOwner) { bookList ->

                if (bookList != null) {
                    bookAdapter.differ.submitList(bookList)

                }
            }
        }

    }

}

