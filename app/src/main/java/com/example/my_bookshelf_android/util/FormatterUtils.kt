package com.example.my_bookshelf_android.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream

object FormatterUtils {

    private val TAG = FormatterUtils::class.java.simpleName

    fun Bitmap?.toBase64(): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
    }

    fun Uri.toBitmap(context: Context): Bitmap? {
        return try {
            val inputStream = context.contentResolver?.openInputStream(this)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e(TAG, "format error: $e")
            null
        }
    }

}
