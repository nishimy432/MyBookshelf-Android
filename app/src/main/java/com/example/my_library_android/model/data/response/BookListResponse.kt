package com.example.my_library_android.model.data.response

import com.google.gson.annotations.SerializedName

data class BookListResponse(
    val status: Int,
    @SerializedName("result")
    val results: List<BookResult>?,
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("current_page")
    val currentPage: Int,
    val limit: Int
)
