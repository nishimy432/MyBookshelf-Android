package com.example.my_bookshelf_android.view.activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.my_bookshelf_android.R
import com.example.my_bookshelf_android.databinding.ActivityMainBinding
import com.example.my_bookshelf_android.util.KeyboardUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_navigation)
                as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_book_list, R.id.navigation_logout
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        var currentItemId: Int? = null

        // bookListFragment の再生成を抑制
        navView.setOnItemSelectedListener { item ->
            if (currentItemId == item.itemId) return@setOnItemSelectedListener false

            if (currentItemId != null && item.itemId == R.id.navigation_book_list) {
                navController.popBackStack(R.id.navigation_book_list, false)
            } else {
                navController.navigate(item.itemId)
            }
            currentItemId = item.itemId
            true
        }

        // 編集画面では bottomNavigationViewを消す
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.visibility =
                if (destination.id == R.id.navigation_book_edit) View.GONE
                else View.VISIBLE
        }
    }

    // 戻るボタンを押した時の処理
    // リスト画面とログイン画面の時はアプリを終了
    override fun onBackPressed() {
        findNavController(R.id.nav_host_fragment_activity_navigation).run {
            currentDestination?.let { currentDestination ->
                when (currentDestination.id) {
                    R.id.navigation_book_list, R.id.navigation_logout -> finish()
                    else -> popBackStack()
                }
            }
        }
    }

    // 背面タップでキーボードを閉じる
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val focusView = currentFocus ?: return false
        KeyboardUtils.hideKeyboard(focusView)

        return false
    }
}
