package com.techsultan.bookxchange.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.api.RetrofitService
import com.techsultan.bookxchange.model.Book
import com.techsultan.bookxchange.model.GoogleBooksResponse
import com.techsultan.bookxchange.model.VolumeInfo
import com.techsultan.bookxchange.model.booksmodel.Result

class BookRepository(val context: Context) {

    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth
    private val bookLiveData = MutableLiveData<List<Book>>()
    private val bookService = RetrofitService.booksApi

    private val _books = MutableLiveData<GoogleBooksResponse?>()
    val books: MutableLiveData<GoogleBooksResponse?> get() = _books



    init {

        fetchBooks(context)
        //fetchGoogleBooks()
    }

    //suspend fun getGoogleBooks(query: String) = RetrofitService.api.getBooks(query)

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

    fun myBooksList(): MutableLiveData<List<Book>> {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("books")
                .get()
                .addOnSuccessListener { documents ->
                    val userBooks = mutableListOf<Book>()
                    for (document in documents) {
                        val book = document.toObject(Book::class.java)
                        userBooks.add(book)
                    }
                    bookLiveData.value = userBooks
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show()
                }
        }
        return bookLiveData
    }



    /*suspend fun fetchGoogleBooks(): List<VolumeInfo> {

         try {
            val response = googleBookService.getGoogle(getRandomSearchQuery())
             val googleBooksResponse = response.body()

             if (response.isSuccessful && googleBooksResponse?.volumeInfo != null) {

                 return googleBooksResponse.volumeInfo.let { listOf(it) }
             } else {
                 Log.d("Google Books", "GOOGLE_BOOKS: $googleBooksResponse")
                 Log.d("Google Books", "GOOGLE_BOOKS: $response")
                 throw Exception("Error fetching books")
             }
         } catch (e: Exception) {

             Log.d("FETCH BOOKS", e.message!!)
             throw e
         }
    }*/
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

    private fun getRandomSearchQuery(): String {
        // Define a list of random search queries
        val randomQueries = listOf(
            "History",
          /*  "Android Development",
            "Machine Learning",
            "Science Fiction",*/

        )
        return randomQueries.random()
    }


}