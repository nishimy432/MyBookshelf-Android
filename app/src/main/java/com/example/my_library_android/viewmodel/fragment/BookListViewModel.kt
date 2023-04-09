package com.example.my_library_android.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_library_android.model.SessionManager
import com.example.my_library_android.model.data.response.BookListResponse
import com.example.my_library_android.model.data.response.BookResult
import com.example.my_library_android.model.repository.BookRepository
import kotlinx.coroutines.launch

class BookListViewModel : ViewModel() {
    private val _bookList = MutableLiveData<List<BookResult>?>()
    val bookList: LiveData<List<BookResult>?>
        get() = _bookList

    private var currentList: List<BookResult> = listOf()
    private var currentBook: BookListResponse? = null
    private var currentPage = FIRST_PAGE

    companion object {
        private val TAG = BookListViewModel::class.java.simpleName
        private const val LIMIT = 20
        private const val FIRST_PAGE = 1
    }

    fun fetchBookList() {

        viewModelScope.launch {
            try {
                currentBook = BookRepository().bookList(
                    SessionManager.fetchAuthToken(),
                    LIMIT,
                    currentPage
                )
                currentBook?.results?.let { list ->
                    if (currentList.size <= LIMIT * currentPage) {
                        currentList = currentList.take(LIMIT * (currentPage - FIRST_PAGE)) + list
                    } else {
                        // 変更が含まれる箇所だけ書き換える
                        currentList = currentList.take(LIMIT * (currentPage - FIRST_PAGE)) +
                                list + currentList.takeLast(currentList.size - LIMIT * currentPage)
                        editCurrentPage(currentList.size - FIRST_PAGE)
                    }
                }
                _bookList.value = currentList
            } catch (e: Exception) {
                Log.e(TAG, "$e")
            }
        }
    }

    fun canLoadBookList(): Boolean {
        currentBook?.let {
            if (it.totalPages > currentPage) {
                currentPage++
                return true
            }
        }
        return false
    }

    fun editCurrentPage(position: Int) {
        currentPage = position / LIMIT + FIRST_PAGE
    }
}
