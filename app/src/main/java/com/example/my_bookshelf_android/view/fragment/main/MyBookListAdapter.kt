package com.example.my_bookshelf_android.view.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_bookshelf_android.R
import com.example.my_bookshelf_android.model.data.response.BookResult

class MyBookListAdapter(
    private val activity: BookListFragment
) : RecyclerView.Adapter<MyBookListAdapter.ViewHolder>() {

    private val bookListDiffer: AsyncListDiffer<BookResult> =
        AsyncListDiffer(this, BookListDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.book_list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookListItem = bookListDiffer.currentList[position]
        holder.name.text = bookListItem.name
        holder.price.text = bookListItem.price?.let {
            activity.resources.getString(R.string.string_price, it)
        } ?: ""
        holder.purchaseDate.text = bookListItem.purchaseDate?.let {
            activity.resources.getString(R.string.string_purchase_date, it)
        } ?: ""
        bookListItem.image?.let {
            Glide.with(activity)
                .load(it)
                .into(holder.image)
        } ?: holder.image.setImageResource(R.drawable.default_image)

        holder.itemView.setOnClickListener {
            activity.findNavController().navigate(
                BookListFragmentDirections.actionListToEdit(holder.adapterPosition, bookListItem)
            )
        }
    }

    override fun getItemCount(): Int = bookListDiffer.currentList.size

    fun submitList(list: List<BookResult>) {
        bookListDiffer.submitList(list)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val purchaseDate: TextView = itemView.findViewById(R.id.purchase_date)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    class BookListDiffCallback : DiffUtil.ItemCallback<BookResult>() {
        override fun areItemsTheSame(oldItem: BookResult, newItem: BookResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookResult, newItem: BookResult): Boolean {
            return oldItem == newItem
        }
    }
}
