package com.victor.booktracker.ui.fragments.book_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.booktracker.R
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.databinding.BookListFragmentBinding
import com.victor.booktracker.ui.adapters.BookRowAdapter
import com.victor.booktracker.ui.fragments.book_details.BookDetailsFragment

class BookListFragment : Fragment(R.layout.book_list_fragment) {
    private lateinit var bookListFragmentBinding: BookListFragmentBinding

    private lateinit var bookRowAdapter: BookRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookListFragmentBinding = BookListFragmentBinding.inflate(inflater, container, false)
        return bookListFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookRowAdapter = BookRowAdapter { bookClicked ->
            navigateToDetails(bookClicked.bookId)
        }

        bookListFragmentBinding.rvBooksList.layoutManager = LinearLayoutManager(requireContext())

        bookListFragmentBinding.rvBooksList.adapter = bookRowAdapter

        loadSampleData()
    }

    fun loadSampleData() {
        val listOfBooks = listOf(
            BookEntity(1, "My first book", null, null, null, 120, 2020, null, false),
            BookEntity(2, "My Second book", null, null, null, 200, 1990, null, false),
            BookEntity(3, "My third book", null, null, null, 300, 1920, null, false)
        )

        bookRowAdapter.submitList(listOfBooks)
    }

    private fun navigateToDetails(bookId: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main_activity, BookDetailsFragment.newInstance(bookId))
            addToBackStack("BookDetails")
        }
    }
}