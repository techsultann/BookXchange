package com.techsultan.bookxchange.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.model.booksmodel.Result
import com.techsultan.bookxchange.repository.ChatRepository
import com.techsultan.bookxchange.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {
    private val _books = MutableLiveData<List<Result>>()
    val books : LiveData<List<Result>> = _books
    private val _searchBooks = MutableLiveData<List<Book>>()
    val searchBooks : LiveData<List<Book>> = _searchBooks

    fun searchBooks(query: String) {
        viewModelScope.launch {
            val result = repository.searchBooks(query)
            _searchBooks.postValue(result)
        }
    }

    fun fetchBooks() {
        viewModelScope.launch {
            try {
                val bookList = repository.fetchBooks()
                _books.value = bookList
                Log.d("FETCH GOOGLE BOOKS", "Books fetched successfully")
            } catch (e: Exception) {
                Log.d("FETCH GOOGLE BOOKS", e.message!!)
            }
        }
    }


}
class SearchViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}