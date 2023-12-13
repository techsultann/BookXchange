package com.techsultan.bookxchange.api

import com.techsultan.bookxchange.model.GoogleBooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("books")
    suspend fun getGoogle(

        @Query("q")
        query: String,
    ) : Response<GoogleBooksResponse>

}