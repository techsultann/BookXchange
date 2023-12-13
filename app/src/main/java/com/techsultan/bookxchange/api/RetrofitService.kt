package com.techsultan.bookxchange.api

import com.techsultan.bookxchange.util.Constants.Companion.BOOK_URL
import com.techsultan.bookxchange.util.Constants.Companion.GOOGLE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {

        private val retrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

           /* Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()*/

            Retrofit.Builder()
                .baseUrl(BOOK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val booksApi: BooksApi by lazy { retrofit.create(BooksApi::class.java) }
        //val api: GoogleBooksApi by lazy { retrofit.create(GoogleBooksApi::class.java) }
        
    }
}