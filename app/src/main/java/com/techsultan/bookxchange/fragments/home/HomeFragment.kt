package com.techsultan.bookxchange.fragments.home

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
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.MainActivity
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.R.*
import com.techsultan.bookxchange.adapter.BookAdapter
import com.techsultan.bookxchange.databinding.FragmentHomeBinding
import com.techsultan.bookxchange.viewmodel.BookViewModel


class HomeFragment : Fragment(), MenuProvider {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookAdapter: BookAdapter
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bookViewModel = (activity as MainActivity).viewModel

        setupRecyclerView()


        binding.fabBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addBookFragment, null)
        }

    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = bookAdapter

            bookViewModel.bookResult().observe(viewLifecycleOwner) { bookList ->

                if (bookList != null) {

                    bookAdapter.differ.submitList(bookList)
                }
            }
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.action_settings -> {
            }
            R.id.notificationsFragment -> {

            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}