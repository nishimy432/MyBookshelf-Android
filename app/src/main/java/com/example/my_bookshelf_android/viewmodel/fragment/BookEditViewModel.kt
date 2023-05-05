package com.example.my_bookshelf_android.viewmodel.fragment

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.example.my_bookshelf_android.R
import com.example.my_bookshelf_android.model.data.ExecutionResult
import com.example.my_bookshelf_android.model.data.request.BookDataRequest
import com.example.my_bookshelf_android.model.repository.BookRepository
import com.example.my_bookshelf_android.util.FormatterUtils.toBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookEditViewModel : ViewModel() {
    private var bitmapImage: Bitmap? = null
    private val _result = MutableLiveData<ExecutionResult>()
    val result: LiveData<ExecutionResult>
        get() = _result

    fun editBook(
        token: String,
        id: Int,
        name: String,
        price: Int?,
        purchaseDate: String?
    ) {
        viewModelScope.launch {
            try {
                BookRepository().editBook(
                    token,
                    id,
                    BookDataRequest(name, bitmapImage.toBase64(), price, purchaseDate)
                )
                _result.value = ExecutionResult(isSuccess = true)
            } catch (e: Exception) {
                _result.value = ExecutionResult(
                    false,
                    R.string.error_title_connection,
                    mutableListOf(R.string.error_message_book_edit_failed)
                )
                Log.e(TAG, "$e")
            }
        }
    }

    // Glide...submit() は back thread で行う
    fun toBitmapFromUrlString(glideRequest: RequestManager, url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                bitmapImage = glideRequest
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()
            } catch (e: Exception) {
                bitmapImage = null
                Log.e(TAG, "$e")
            }
        }
    }

    fun setupBitmap(currentBitmapImage: Bitmap?) {
        bitmapImage = currentBitmapImage
    }

    companion object {
        private val TAG = BookEditViewModel::class.java.simpleName
    }

}
