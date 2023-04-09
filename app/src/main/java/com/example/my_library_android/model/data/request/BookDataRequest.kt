package com.example.my_library_android.model.data.request

import com.google.gson.annotations.SerializedName

data class BookDataRequest(
    val name: String,
    val image: String?,
    val price: Int?,
    @SerializedName("purchase_date")
    val purchaseDate: String?
)
