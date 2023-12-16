package com.techsultan.bookxchange.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.model.GoogleBooksResponse
import com.techsultan.bookxchange.model.Resource
import com.techsultan.bookxchange.model.VolumeInfo
import com.techsultan.bookxchange.model.booksmodel.Result
import com.techsultan.bookxchange.repository.BookRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class BookViewModel(
    app: Application,
    private val repository: BookRepository
) : AndroidViewModel(app) {

    //val getGoogleBooks : MutableLiveData<Resource<GoogleBooksResponse>> = MutableLiveData()
    private val _googleBooks = MutableLiveData<List<VolumeInfo>>()
    val googleBooks : LiveData<List<VolumeInfo>> = _googleBooks

    fun bookResult() = repository.fetchBooks(context = getApplication())
    fun myBooksResult() = repository.myBooksList()

}