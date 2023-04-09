package com.example.my_library_android.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.my_library_android.R
import com.example.my_library_android.databinding.ActivityBookAddBinding
import com.example.my_library_android.model.SessionManager
import com.example.my_library_android.model.data.ExecutionResult
import com.example.my_library_android.util.AlertDialogUtils
import com.example.my_library_android.util.FormatterUtils.toBitmap
import com.example.my_library_android.util.KeyboardUtils
import com.example.my_library_android.util.ValidationUtils
import com.example.my_library_android.view.fragment.dialog.DatePickerDialogFragment
import com.example.my_library_android.viewmodel.activity.BookAddViewModel

class BookAddActivity : AppCompatActivity(), DatePickerDialogFragment.OnSelectedDateListener {

    private val bookAddViewModel: BookAddViewModel by viewModels()
    private lateinit var binding: ActivityBookAddBinding

    private var bitmapImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val launcher = setupLauncher() // 画像選択を準備

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        SessionManager.setupSharedPreferences(this)

        binding.addPurchaseDate.setOnClickListener {
            DatePickerDialogFragment().show(supportFragmentManager, "datePicker")
        }

        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }

        bookAddViewModel.result.observe(this) {
            showBookList(it)
        }

    }

    // 画像選択を準備
    private fun setupLauncher(): ActivityResultLauncher<String> {
        val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                bitmapImage = it.toBitmap(this)
                Glide.with(this).load(it).into(binding.addImage)
            }
        }
        return launcher
    }

    override fun selectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date: String = getString(R.string.string_format, year, monthOfYear + 1, dayOfMonth)
        binding.addPurchaseDate.setText(date)
    }

    // オプションメニューを表示
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_completion_menu, menu)
        return true
    }

    // オプションメニューをクリックした時の処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_completion -> {
                binding.loading.visibility = View.VISIBLE

                val name = binding.addName.text.toString()
                val price = binding.addPrice.text.toString()
                val purchaseDate = binding.addPurchaseDate.text.toString()

                val validateResult = ValidationUtils.isInputBookDataValid(name, price, purchaseDate)

                if (validateResult.isSuccess) {
                    bookAddViewModel.addBook(
                        SessionManager.fetchAuthToken(),
                        name,
                        bitmapImage,
                        price.toIntOrNull(),
                        purchaseDate
                    )
                } else {
                    binding.loading.visibility = View.GONE
                    AlertDialogUtils.showAlertDialog(
                        this,
                        supportFragmentManager,
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
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        } else {
            binding.loading.visibility = View.GONE
            AlertDialogUtils.showAlertDialog(this, supportFragmentManager, result)
        }
    }

    // 背面タップでキーボードを閉じる
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val focusView = currentFocus ?: return false
        KeyboardUtils.hideKeyboard(focusView)

        return false
    }
}
