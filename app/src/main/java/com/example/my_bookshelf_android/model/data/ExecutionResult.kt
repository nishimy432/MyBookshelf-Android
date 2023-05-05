package com.example.my_bookshelf_android.model.data

import com.example.my_bookshelf_android.R

data class ExecutionResult(
    var isSuccess: Boolean = false,
    var errorTitle: Int = R.string.empty,
    var errorMessage: MutableList<Int> = mutableListOf()
)
