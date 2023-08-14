package com.techsultan.bookxchange.api

import com.techsultan.bookxchange.model.GoogleBooksResponse
import com.techsultan.bookxchange.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("volumes")
    fun getBooks(

        @Query("q")
        query: String,
        @Query("api_key")
        apiKey : String = API_KEY
    ) : Call<List<GoogleBooksResponse>>



}