package com.example.my_library_android.view.fragment.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.my_library_android.R
import com.example.my_library_android.databinding.FragmentBookEditBinding
import com.example.my_library_android.model.SessionManager
import com.example.my_library_android.model.data.ExecutionResult
import com.example.my_library_android.model.data.response.BookResult
import com.example.my_library_android.util.AlertDialogUtils
import com.example.my_library_android.util.FormatterUtils.toBitmap
import com.example.my_library_android.util.ValidationUtils
import com.example.my_library_android.view.fragment.dialog.DatePickerDialogFragment
import com.example.my_library_android.viewmodel.fragment.BookEditViewModel

const val EDIT_POSITION_KEY = "edit_position_key"
const val POSITION = "position"

class BookEditFragment : Fragment(R.layout.fragment_book_edit),
    DatePickerDialogFragment.OnSelectedDateListener {

    private val bookEditViewModel: BookEditViewModel by viewModels()
    private var _binding: FragmentBookEditBinding? = null
    private val args: BookEditFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookEditBinding.bind(view)

        setupEditItem(args.editItem)

        val launcher = setupLauncher() // 画像取得を準備

        SessionManager.setupSharedPreferences(requireContext())

        binding.editPurchaseDate.setOnClickListener {
            DatePickerDialogFragment().show(childFragmentManager, "datePicker")
        }

        binding.editImage.setOnClickListener {
            launcher.launch("image/*")
        }

        setHasOptionsMenu(true)

        bookEditViewModel.result.observe(viewLifecycleOwner) {
            showBookList(it)
        }

    }

    private fun setupEditItem(editItem: BookResult) {
        binding.editName.setText(editItem.name)
        binding.editPrice.setText(editItem.price?.toString())
        binding.editPurchaseDate.setText(editItem.purchaseDate)
        editItem.image?.let {
            val glideRequest: RequestManager = Glide.with(requireContext())
            bookEditViewModel.toBitmapFromUrlString(glideRequest, it)
            glideRequest
                .asBitmap()
                .load(it)
                .into(binding.editImage)
        }
    }

    // 画像選択を準備
    private fun setupLauncher(): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                bookEditViewModel.setupBitmap(it.toBitmap(requireContext()))
                Glide.with(requireContext())
                    .load(it)
                    .into(binding.editImage)
            }
        }
    }

    override fun selectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date: String = getString(R.string.string_format, year, monthOfYear + 1, dayOfMonth)
        binding.editPurchaseDate.setText(date)
    }

    // オプションメニューを表示
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_completion_menu, menu)
    }

    // オプションメニューをクリックした時の処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            R.id.menu_completion -> {
                binding.loading.visibility = View.VISIBLE

                val name = binding.editName.text.toString()
                val price = binding.editPrice.text.toString()
                val purchaseDate = binding.editPurchaseDate.text.toString()

                val validateResult = ValidationUtils.isInputBookDataValid(name, price, purchaseDate)

                if (validateResult.isSuccess) {
                    bookEditViewModel.editBook(
                        SessionManager.fetchAuthToken(),
                        args.editItem.id,
                        name,
                        price.toIntOrNull(),
                        purchaseDate
                    )
                } else {
                    binding.loading.visibility = View.GONE
                    AlertDialogUtils.showAlertDialog(
                        requireContext(),
                        childFragmentManager,
                        validateResult
                    )
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBookList(result: ExecutionResult) {
        if (result.isSuccess) {
            setFragmentResult(
                EDIT_POSITION_KEY,
                bundleOf(POSITION to args.position)
            )
            findNavController().popBackStack()
        } else {
            binding.loading.visibility = View.GONE
            AlertDialogUtils.showAlertDialog(requireContext(), childFragmentManager, result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
