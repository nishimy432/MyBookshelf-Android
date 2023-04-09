package com.example.my_library_android.model

import io.github.cdimascio.dotenv.dotenv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofitクライアントインスタンスを初期化する
object ApiClient {
    private lateinit var apiService: ApiService
    private val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    fun getApiService(): ApiService {
        // apiService がまだ初期化されていなかった場合初期化
        if (!::apiService.isInitialized) {
            apiService = Retrofit.Builder()
                .baseUrl(dotenv["BASE_URL"])
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        return apiService
    }

}
