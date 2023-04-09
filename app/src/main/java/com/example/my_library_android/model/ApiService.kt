package com.example.my_library_android.model

import com.example.my_library_android.model.data.Endpoints
import com.example.my_library_android.model.data.request.BookDataRequest
import com.example.my_library_android.model.data.request.UserRequest
import com.example.my_library_android.model.data.response.BookDataResponse
import com.example.my_library_android.model.data.response.BookListResponse
import com.example.my_library_android.model.data.response.LogoutResponse
import com.example.my_library_android.model.data.response.UserResponse
import retrofit2.http.*

interface ApiService {
    @POST(Endpoints.LOGIN_URL)
    suspend fun login(@Body request: UserRequest): UserResponse

    @POST(Endpoints.SIGN_UP_URL)
    suspend fun signup(@Body request: UserRequest): UserResponse

    @DELETE(Endpoints.LOGOUT)
    suspend fun logout(@Header("Authrization") token: String): LogoutResponse

    @GET(Endpoints.BOOKS)
    suspend fun bookList(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): BookListResponse

    @POST(Endpoints.BOOKS)
    suspend fun addBook(
        @Header("Authorization") token: String,
        @Body request: BookDataRequest
    ): BookDataResponse

    @PUT(Endpoints.BOOK_EDIT)
    suspend fun editBook(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: BookDataRequest
    ): BookDataResponse

}
