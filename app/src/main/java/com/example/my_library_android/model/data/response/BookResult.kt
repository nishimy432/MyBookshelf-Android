package com.example.my_library_android.model.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookResult(
    val id: Int,
    val name: String,
    val image: String?,
    val price: Int?,
    @SerializedName("purchase_date")
    val purchaseDate: String?
) : Parcelable
