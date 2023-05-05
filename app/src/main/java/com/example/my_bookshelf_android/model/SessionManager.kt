package com.example.my_bookshelf_android.model

import android.content.Context
import android.content.SharedPreferences
import com.example.my_bookshelf_android.R
import io.github.cdimascio.dotenv.dotenv

// ユーザーのデバイスでトークンを保存及びフェッチする
object SessionManager {
    private lateinit var preferences: SharedPreferences
    private val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    fun setupSharedPreferences(context: Context) {
        preferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    // token を保存する
    fun saveAuthToken(token: String) {
        preferences.edit()
            .putString(dotenv["USER_TOKEN"], token)
            .apply()
    }

    // token があるか
    fun hasToken(): Boolean =
        preferences.getString(dotenv["USER_TOKEN"], null) != null

    // token を返す
    fun fetchAuthToken(): String =
        preferences.getString(dotenv["USER_TOKEN"], null) ?: run {
            throw NullPointerException("token is null")
        }

    // token の削除
    fun deleteAuthToken() {
        preferences.edit()
            .clear()
            .apply()
    }

}
