package com.example.my_library_android.model.repository

import com.example.my_library_android.model.ApiClient
import com.example.my_library_android.model.data.request.UserRequest
import com.example.my_library_android.model.data.response.LogoutResponse
import com.example.my_library_android.model.data.response.UserResponse

class UserRepository {

    suspend fun login(request: UserRequest): UserResponse =
        ApiClient.getApiService().login(request)

    suspend fun signup(request: UserRequest): UserResponse =
        ApiClient.getApiService().signup(request)

    suspend fun logout(token: String): LogoutResponse =
        ApiClient.getApiService().logout(token)

}
