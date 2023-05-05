package com.example.my_bookshelf_android.viewmodel.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_bookshelf_android.R
import com.example.my_bookshelf_android.model.SessionManager
import com.example.my_bookshelf_android.model.data.ExecutionResult
import com.example.my_bookshelf_android.model.data.request.UserRequest
import com.example.my_bookshelf_android.model.repository.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    private var token: String? = null
    private val _result = MutableLiveData<ExecutionResult>()
    val result: LiveData<ExecutionResult>
        get() = _result

    fun signup(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val response = UserRepository().signup(UserRequest(email, password))
                token = response.result.token
                _result.value = ExecutionResult(isSuccess = true)
            } catch (e: Exception) {
                _result.value = ExecutionResult(
                    false,
                    R.string.error_title_sign_in,
                    mutableListOf(R.string.error_message_sign_up_failed)
                )
                Log.e(TAG, "Error: $e")
            }
        }
    }

    fun saveToken() {
        token?.let { SessionManager.saveAuthToken(it) }
    }

    companion object {
        private val TAG = SignupViewModel::class.java.simpleName
    }

}
