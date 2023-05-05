package com.example.my_bookshelf_android.viewmodel.activity

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_bookshelf_android.R
import com.example.my_bookshelf_android.model.data.ExecutionResult
import com.example.my_bookshelf_android.model.data.request.BookDataRequest
import com.example.my_bookshelf_android.model.repository.BookRepository
import com.example.my_bookshelf_android.util.FormatterUtils.toBase64
import kotlinx.coroutines.launch

class BookAddViewModel : ViewModel() {

    private val _result = MutableLiveData<ExecutionResult>()
    val result: LiveData<ExecutionResult>
        get() = _result

    fun addBook(
        token: String,
        name: String,
        bitmapImage: Bitmap?,
        price: Int?,
        purchaseDate: String?
    ) {
        viewModelScope.launch {
            try {
                BookRepository().addBook(
                    token,
                    BookDataRequest(name, bitmapImage.toBase64(), price, purchaseDate)
                )
                _result.value = ExecutionResult(isSuccess = true)
            } catch (e: Exception) {
                _result.value = ExecutionResult(
                    false,
                    R.string.error_title_connection,
                    mutableListOf(R.string.error_message_book_add_failed)
                )
                Log.e(BookAddViewModel::class.java.simpleName, "$e")
            }
        }
    }

}
