package com.example.my_bookshelf_android.view.fragment.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnSelectedDateListener {
        fun selectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int)
    }

    private var listener: OnSelectedDateListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = if (parentFragment is OnSelectedDateListener) {
            parentFragment as OnSelectedDateListener
        } else {
            context as? OnSelectedDateListener
        }

        if (listener == null) {
            throw ClassCastException("$context must implement OnSelectedDateListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calender = Calendar.getInstance()

        return DatePickerDialog(
            requireContext(),
            this,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MARCH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        listener?.selectedDate(year, monthOfYear, dayOfMonth)
    }
}
