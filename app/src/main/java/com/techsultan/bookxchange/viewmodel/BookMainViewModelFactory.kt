package com.techsultan.bookxchange.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techsultan.bookxchange.repository.BookRepository

class BookMainViewModelFactory(
    private val app: Application,
    private val repository: BookRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(app, repository) as T
    }
}