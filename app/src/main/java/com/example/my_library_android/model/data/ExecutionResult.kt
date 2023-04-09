package com.example.my_library_android.model.data

import com.example.my_library_android.R

data class ExecutionResult(
    var isSuccess: Boolean = false,
    var errorTitle: Int = R.string.empty,
    var errorMessage: MutableList<Int> = mutableListOf()
)
