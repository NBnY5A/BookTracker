package com.victor.booktracker.ui.fragments.book_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.victor.booktracker.R
import com.victor.booktracker.databinding.BookDetailFragmentBinding
import com.victor.booktracker.ui.view_model.BookViewModel
import kotlin.getValue

class BookDetailsFragment : Fragment(R.layout.book_detail_fragment) {
    private lateinit var bookDetailsFragmentBinding: BookDetailFragmentBinding

    private val viewModel: BookViewModel by activityViewModels()

    companion object {
        private const val ARG_BOOK_ID = "arg_book_id"

        fun newInstance(bookId: Int): BookDetailsFragment {
            val fragment = BookDetailsFragment()
            val args = Bundle()
            args.putInt(ARG_BOOK_ID, bookId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookDetailsFragmentBinding = BookDetailFragmentBinding.bind(view)

        val bookId = arguments?.getInt(ARG_BOOK_ID) ?: -1

        viewModel.getBookById(bookId)

        bookDetailsFragmentBinding.tvBookTitle.text = viewModel.selectedBook.value?.bookName
        bookDetailsFragmentBinding.tvBookPages.text =
            viewModel.selectedBook.value?.totalOfPages.toString()
    }
}