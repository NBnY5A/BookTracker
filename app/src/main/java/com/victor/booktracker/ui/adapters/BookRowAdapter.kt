package com.victor.booktracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.databinding.BookRowFragmentBinding

class BookRowAdapter(
    private val onBookClicked: (BookEntity) -> Unit
) :
    RecyclerView.Adapter<BookRowAdapter.BookViewHolder>() {

    private var listOfBooks: List<BookEntity> = emptyList()

    fun submitList(newBooks: List<BookEntity>) {
        listOfBooks = newBooks
        notifyDataSetChanged()
    }

    class BookViewHolder(val binding: BookRowFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        val binding =
            BookRowFragmentBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )

        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(
        viewHolder: BookViewHolder,
        position: Int
    ) {
        val book = listOfBooks[position]
        viewHolder.binding.tvTitle.text = book.bookName
        viewHolder.binding.tvYear.text = book.yearOfPublished.toString()
        viewHolder.binding.tvPages.text = "${book.totalOfPages} páginas"

        viewHolder.binding.root.setOnClickListener {
            onBookClicked(book)
        }
    }

    override fun getItemCount(): Int {
        return listOfBooks.size
    }

}