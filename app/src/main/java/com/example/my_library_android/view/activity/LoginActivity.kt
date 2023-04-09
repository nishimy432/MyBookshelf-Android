package com.example.my_library_android.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.my_library_android.databinding.ActivityLoginBinding
import com.example.my_library_android.model.SessionManager
import com.example.my_library_android.model.data.ExecutionResult
import com.example.my_library_android.util.AlertDialogUtils
import com.example.my_library_android.util.KeyboardUtils
import com.example.my_library_android.util.ValidationUtils
import com.example.my_library_android.viewmodel.activity.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SessionManager.setupSharedPreferences(this)

        // トークンが存在していたらリストへ遷移
        if (SessionManager.hasToken()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            val validateResult = ValidationUtils.isLoginValid(email, password)

            if (validateResult.isSuccess) {
                loginViewModel.login(email, password)
            } else {
                binding.loading.visibility = View.GONE
                AlertDialogUtils.showAlertDialog(
                    this,
                    supportFragmentManager,
                    validateResult
                )
            }
        }

        binding.signup.setOnClickListener {
            // サインイン画面へ画面遷移
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.result.observe(this) { result ->
            showBookList(result)
        }
    }

    private fun showBookList(result: ExecutionResult) {
        if (result.isSuccess) {
            // トークンを保存
            loginViewModel.saveToken()
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
