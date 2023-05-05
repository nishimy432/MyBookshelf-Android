package com.example.my_bookshelf_android.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.my_bookshelf_android.databinding.ActivitySignupBinding
import com.example.my_bookshelf_android.model.data.ExecutionResult
import com.example.my_bookshelf_android.util.AlertDialogUtils
import com.example.my_bookshelf_android.util.KeyboardUtils
import com.example.my_bookshelf_android.util.ValidationUtils
import com.example.my_bookshelf_android.viewmodel.activity.SignupViewModel

class SignupActivity : AppCompatActivity() {

    private val signupViewModel: SignupViewModel by viewModels()
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ツールバーに戻るボタンをセット
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.signup.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            val validateResult = ValidationUtils.isSignupValid(email, password, confirmPassword)

            if (validateResult.isSuccess) {
                signupViewModel.signup(email, password)
            } else {
                binding.loading.visibility = View.GONE
                AlertDialogUtils.showAlertDialog(
                    this,
                    supportFragmentManager,
                    validateResult
                )
            }
        }

        signupViewModel.result.observe(this) { result ->
            showBookList(result)
        }
    }

    // 戻るボタンを推した時の処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBookList(result: ExecutionResult) {
        if (result.isSuccess) {
            // トークンを保存
            signupViewModel.saveToken()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
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
