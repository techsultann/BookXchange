package com.techsultan.bookxchange.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.model.Book

class BookRepository(val context: Context) {

    private val fireStore = Firebase.firestore
    private val bookLiveData = MutableLiveData<List<Book>>()



    init {

        fetchBooks(context)

    }

    fun fetchBooks(context : Context): LiveData<List<Book>> {

        val allBooksCollection = fireStore.collectionGroup("books")

        allBooksCollection.get()
            .addOnSuccessListener { querySnapShot ->
                val booksList = mutableListOf<Book>()

                for (document in querySnapShot) {

                    val book = document.toObject(Book::class.java)
                    booksList.add(book)
                }
                bookLiveData.value = booksList
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error fetching books", Toast.LENGTH_SHORT).show()
                bookLiveData.value = emptyList()
            }

        return bookLiveData
    }
}