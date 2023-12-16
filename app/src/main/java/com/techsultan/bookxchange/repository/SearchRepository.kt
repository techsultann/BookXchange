package com.techsultan.bookxchange.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.api.RetrofitService
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.model.booksmodel.Result
import kotlinx.coroutines.tasks.await

class SearchRepository {
    private val bookService = RetrofitService.booksApi
    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun fetchBooks(): List<Result> {
        try {
            val response = bookService.getBooks()
            val booksResponse = response.body()

            if (booksResponse?.results != null){

                return booksResponse.results
            } else {
                throw Exception("Error fetching books")
            }
        } catch (e: Exception) {
            Log.d("FETCH BOOKS", e.message!!)
            throw e
        }
    }


    suspend fun searchBooks(query: String): List<Book> {

        val booksCollection = fireStore.collection("books")
        val author = booksCollection.whereEqualTo("author", query)
        val bookName = booksCollection.whereEqualTo("bookName", query)

        try {
            val titleResult = author.get().await()
            val authorResult = bookName.get().await()

            val titleBooks = titleResult.map { document ->
                // Convert Firestore document to Book object
                Book(
                    bookName = document.getString("title") ?: "",
                    author = document.getString("author") ?: "",
                    // Add other relevant fields
                )
            }

            val authorBooks = authorResult.map { document ->
                // Convert Firestore document to Book object
                Book(
                    bookName = document.getString("title") ?: "",
                    author = document.getString("author") ?: "",
                    // Add other relevant fields
                )
            }

                // Combine the results from title and author queries
                return (titleBooks + authorBooks).distinctBy { it } // Remove duplicates

        } catch (e: Exception) {
            return emptyList()
        }
    }

}