package com.techsultan.bookxchange.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.repository.BookRepository

class BookViewModel(
    app: Application,
    private val repository: BookRepository
) : AndroidViewModel(app) {


    fun bookResult() = repository.fetchBooks(context = getApplication())
    fun googleBooksResult() = repository.getGoogleBooksLiveData()

    fun myBooksResult() = repository.myBooksList()
}