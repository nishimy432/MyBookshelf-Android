package com.example.my_bookshelf_android.util

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.my_bookshelf_android.model.data.ExecutionResult
import com.example.my_bookshelf_android.view.fragment.dialog.AlertDialogFragment

object AlertDialogUtils {

    fun showAlertDialog(
        context: Context,
        supportFragmentManager: FragmentManager,
        result: ExecutionResult
    ) {
        AlertDialogFragment.create(
            context.resources.getString(result.errorTitle),
            result.errorMessage.joinToString(
                separator = "",
                transform = { context.resources.getString(it) }
            )
        ).show(supportFragmentManager, AlertDialogFragment::class.simpleName)
    }
}
