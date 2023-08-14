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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookRepository(val context: Context) {

    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth
    private val bookLiveData = MutableLiveData<List<Book>>()
    private val googleBookService = RetrofitService.api
    private val googleBooksLiveData : MutableLiveData<List<VolumeInfo>?> = MutableLiveData()

    private val _books = MutableLiveData<List<VolumeInfo>>()
    val books: LiveData<List<VolumeInfo>> get() = _books



    init {

        fetchBooks(context)
        fetchGoogleBooks()

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

    fun fetchGoogleBooks() {

        val randomQuery = getRandomSearchQuery()
        googleBookService.getBooks(randomQuery).enqueue(
             object : Callback<List<GoogleBooksResponse>> {
                 override fun onResponse(
                     call: Call<List<GoogleBooksResponse>>,
                     response: Response<List<GoogleBooksResponse>>
                 ) {

                     if (response.isSuccessful) {

                         val booksResponse = response.body()
                         val books = booksResponse?.mapNotNull { it.volumeInfo } ?: emptyList()

                         Log.d("BooksViewModel", "Fetched Books: $googleBooksLiveData")
                         _books.value = books
                         /*googleBooksLiveData.postValue(response.body()!!.sortedByDescending {
                         it.volumeInfo?.title!!.length }.take(10))*/
                     } else {

                         Toast.makeText(context, "Can't retrieve books list", Toast.LENGTH_SHORT).show()
                     }

                 }

                 override fun onFailure(call: Call<List<GoogleBooksResponse>>, t: Throwable) {
                     googleBooksLiveData.postValue(null)

                     Log.e("BooksViewModel", "API Call Failed: ${t.message}", t)
                 }


             }

            )
    }

    private fun getRandomSearchQuery(): String {
        // Define a list of random search queries
        val randomQueries = listOf(
            "Android Development",
            "Machine Learning",
            "Science Fiction",
            "History"
        )
        return randomQueries.random()
    }

    fun getGoogleBooksLiveData() : LiveData<List<VolumeInfo>?> {
        return googleBooksLiveData
    }
}