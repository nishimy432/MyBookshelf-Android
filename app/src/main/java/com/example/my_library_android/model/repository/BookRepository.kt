package com.example.my_library_android.model.repository

import com.example.my_library_android.model.ApiClient
import com.example.my_library_android.model.data.request.BookDataRequest
import com.example.my_library_android.model.data.response.BookDataResponse
import com.example.my_library_android.model.data.response.BookListResponse

class BookRepository {

    suspend fun bookList(
        token: String,
        limit: Int,
        page: Int
    ): BookListResponse =
        ApiClient.getApiService().bookList(token, limit, page)

    suspend fun addBook(
        token: String,
        request: BookDataRequest
    ): BookDataResponse =
        ApiClient.getApiService().addBook(token, request)

    suspend fun editBook(
        token: String,
        id: Int,
        request: BookDataRequest
    ): BookDataResponse =
        ApiClient.getApiService().editBook(token, id, request)

}
