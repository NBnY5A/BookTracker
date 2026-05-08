package com.victor.booktracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.databinding.BookRowFragmentBinding

class BookRowAdapter(
    private val onBookClicked: (BookEntity) -> Unit,
    private val onEditClicked: (BookEntity) -> Unit,
    private val onDeleteClicked: (BookEntity) -> Unit
) : ListAdapter<BookEntity, BookRowAdapter.BookViewHolder>(BookDiffCallback()) {

    class BookViewHolder(val binding: BookRowFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        val binding = BookRowFragmentBinding.inflate(
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
        val book = getItem(position)
        viewHolder.binding.apply {
            tvTitle.text = book.bookName
            tvYear.text = book.yearOfPublished.toString()
            tvPages.text = "${book.lastPageRead ?: 0}/${book.totalOfPages} páginas"

            root.setOnClickListener {
                onBookClicked(book)
            }

            ibEdit.setOnClickListener {
                onEditClicked(book)
            }

            ibDelete.setOnClickListener {
                onDeleteClicked(book)
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<BookEntity>() {
        override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem.bookId == newItem.bookId
        }

        override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem == newItem
        }
    }
}