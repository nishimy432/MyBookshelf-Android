package com.example.my_library_android.view.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.my_library_android.R

class AlertDialogFragment : DialogFragment() {

    companion object {
        private const val TITLE_KEY = "TitleKey"
        private const val MESSAGE_KEY = "MessageKey"

        // AlertDialogFragment インスタンスを生成するメソッド
        fun create(title: String, message: String): AlertDialogFragment {
            return AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE_KEY, title)
                    putString(MESSAGE_KEY, message)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(TITLE_KEY, "") ?: ""
        val message = arguments?.getString(MESSAGE_KEY, "") ?: ""

        return AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(requireContext().getString(R.string.cancel), null)
            .create()
    }
}
