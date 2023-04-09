package com.example.my_library_android.view.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_library_android.R
import com.example.my_library_android.databinding.FragmentBookListBinding
import com.example.my_library_android.model.SessionManager
import com.example.my_library_android.view.activity.BookAddActivity
import com.example.my_library_android.viewmodel.fragment.BookListViewModel

class BookListFragment : Fragment(R.layout.fragment_book_list) {

    private val bookListViewModel: BookListViewModel by viewModels()
    private var _binding: FragmentBookListBinding? = null

    private var isLoading = false

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookListBinding.bind(view)

        val adapter = MyBookListAdapter(this)

        SessionManager.setupSharedPreferences(requireContext())

        initBookList(adapter)

        setFragmentResultListener(EDIT_POSITION_KEY) { _, bundle ->
            bookListViewModel.editCurrentPage(bundle.getInt(POSITION))
        }

        bookListViewModel.bookList.observe(viewLifecycleOwner) {
            binding.newLoading.visibility = View.GONE
            binding.nextLoading.visibility = View.GONE
            binding.noItemText.visibility = View.GONE
            isLoading = false

            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            } else {
                binding.noItemText.visibility = View.VISIBLE
            }
        }

        setHasOptionsMenu(true)
    }

    private fun initBookList(myAdapter: MyBookListAdapter) {
        binding.newLoading.visibility = View.VISIBLE
        isLoading = true

        binding.bookList.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            addOnScrollListener(InfiniteScrollListener())
        }
    }

    // オプションメニューを表示
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_add_menu, menu)
    }

    // オプションメニューをクリックした時の処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this.context, BookAddActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        bookListViewModel.fetchBookList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class InfiniteScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (isLoading ||
                recyclerView.canScrollVertically(1) ||
                !bookListViewModel.canLoadBookList()
            ) return

            isLoading = true
            binding.nextLoading.visibility = View.VISIBLE
            bookListViewModel.fetchBookList()
        }
    }

}
