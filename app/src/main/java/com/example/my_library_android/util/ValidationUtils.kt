package com.example.my_library_android.util

import android.os.Build
import android.util.Patterns
import com.example.my_library_android.R
import com.example.my_library_android.model.data.ExecutionResult
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.regex.Matcher

object ValidationUtils {

    private const val PASSWORD_MINIMUM_LENGTH = 6

    // ログイン時のバリデーションチェック
    fun isLoginValid(
        email: String,
        password: String
    ): ExecutionResult {

        val validLoginResult = ExecutionResult(errorTitle = R.string.error_title_validation)

        if (!isEmptyValid(email) || !isValidEmail(email))
            validLoginResult.errorMessage.add(R.string.error_message_mail_empty)
        if (!isPasswordValid(password))
            validLoginResult.errorMessage.add(R.string.error_message_password_length)

        // バリデーションエラーがないか確認
        if (validLoginResult.errorMessage.isEmpty())
            validLoginResult.isSuccess = true

        return validLoginResult
    }

    // サインアップ時のバリデーションチェック
    fun isSignupValid(
        email: String,
        password: String,
        confirmPassword: String
    ): ExecutionResult {

        val validLoginResult = ExecutionResult(errorTitle = R.string.error_title_validation)

        if (!isEmptyValid(email) || !isValidEmail(email))
            validLoginResult.errorMessage.add(R.string.error_message_mail_empty)
        when {
            !isPasswordValid(password)
            -> validLoginResult.errorMessage.add(R.string.error_message_password_length)
            !isEmptyValid(confirmPassword)
            -> validLoginResult.errorMessage.add(R.string.error_message_confirm_password_empty)
            !isPasswordEqual(password, confirmPassword)
            -> validLoginResult.errorMessage.add(R.string.error_message_password_not_equal)
        }

        // バリデーションエラーがないか確認
        if (validLoginResult.errorMessage.isEmpty())
            validLoginResult.isSuccess = true

        return validLoginResult
    }

    // 書籍情報入力時のバリデーションチェック
    fun isInputBookDataValid(
        name: String,
        price: String?,
        purchaseDate: String?
    ): ExecutionResult {

        val validAddBookResult = ExecutionResult(errorTitle = R.string.error_title_validation)

        if (!isEmptyValid(name))
            validAddBookResult.errorMessage.add(R.string.error_message_name_empty)
        if (!isNumberValid(price))
            validAddBookResult.errorMessage.add(R.string.error_message_price_no_number)
        if (!isDateValid(purchaseDate))
            validAddBookResult.errorMessage.add(R.string.error_message_date_not_date)

        // バリデーションエラーがあるか確認
        if (validAddBookResult.errorMessage.isEmpty())
            validAddBookResult.isSuccess = true

        return validAddBookResult
    }

    // 空文字があるかチェック
    private fun isEmptyValid(text: String?): Boolean {
        return !text.isNullOrBlank()
    }

    // メールアドレスのバリデーションチェック
    private fun isValidEmail(email: String): Boolean {
        val matcher: Matcher = Patterns.EMAIL_ADDRESS.matcher(email)
        return matcher.matches()
    }

    // パスワードのバリデーションチェック
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= PASSWORD_MINIMUM_LENGTH
    }

    // 入力内容が同じかのチェック
    private fun isPasswordEqual(
        password: String,
        confirmPassword: String
    ): Boolean {
        return password == confirmPassword
    }

    // 数字かどうかのチェック
    private fun isNumberValid(number: String?): Boolean {
        if (!isEmptyValid(number)) return true

        return number?.toIntOrNull() != null
    }

    // 日付型のバリデーションチェック
    private fun isDateValid(date: String?): Boolean {
        if (!isEmptyValid(date)) return true

        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.parse(date)
            } else {
                val dateString = date?.replace("-", "/")
                SimpleDateFormat(dateString, Locale.JAPAN)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
