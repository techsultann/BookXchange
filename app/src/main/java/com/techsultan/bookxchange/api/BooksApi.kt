package com.techsultan.bookxchange.api

import com.techsultan.bookxchange.model.GoogleBooksResponse
import com.techsultan.bookxchange.model.booksmodel.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("books")
    suspend fun getBooks(
    ) : Response<BookResponse>
}