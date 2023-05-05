package com.example.my_bookshelf_android.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_bookshelf_android.R
import com.example.my_bookshelf_android.model.SessionManager
import com.example.my_bookshelf_android.model.data.ExecutionResult
import com.example.my_bookshelf_android.model.repository.UserRepository
import kotlinx.coroutines.launch

class LogoutViewModel : ViewModel() {
    private val _result = MutableLiveData<ExecutionResult>()
    val result: LiveData<ExecutionResult>
        get() = _result

    fun logout() {
        viewModelScope.launch {
            try {
                UserRepository().logout(SessionManager.fetchAuthToken())

                SessionManager.deleteAuthToken()
                _result.value = ExecutionResult(isSuccess = true)
            } catch (e: Exception) {
                Log.e(TAG, "$e")
                _result.value = ExecutionResult(
                    false,
                    R.string.error_title_connection,
                    mutableListOf(R.string.error_message_logout_failed)
                )
            }
        }
    }

    companion object {
        private val TAG = LogoutViewModel::class.java.simpleName
    }

}
