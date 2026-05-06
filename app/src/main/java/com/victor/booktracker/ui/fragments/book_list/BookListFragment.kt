package com.victor.booktracker.ui.fragments.book_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.booktracker.R
import com.victor.booktracker.databinding.BookListFragmentBinding
import com.victor.booktracker.ui.adapters.BookRowAdapter
import com.victor.booktracker.ui.fragments.add_book.AddBookFragment
import com.victor.booktracker.ui.fragments.book_details.BookDetailsFragment
import com.victor.booktracker.ui.view_model.BookViewModel
import kotlinx.coroutines.launch

class BookListFragment : Fragment(R.layout.book_list_fragment) {
    private lateinit var bookListFragmentBinding: BookListFragmentBinding

    private lateinit var bookRowAdapter: BookRowAdapter

    private val viewModel: BookViewModel by activityViewModels()

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

        setupRecycleView()
        setupFab()
        observeBooks()
    }

    private fun setupRecycleView() {
        bookRowAdapter = BookRowAdapter { bookClicked ->
            navigateToDetails(bookClicked.bookId)
        }

        bookListFragmentBinding.rvBooksList.layoutManager = LinearLayoutManager(requireContext())

        bookListFragmentBinding.rvBooksList.adapter = bookRowAdapter
    }

    private fun setupFab() {
        bookListFragmentBinding.fabAddBook.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fcv_main_activity, AddBookFragment())
                addToBackStack("AddBook")
            }
        }
    }

    private fun observeBooks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.books.collect { bookList ->
                    bookRowAdapter.submitList(bookList)
                }
            }
        }
    }

    private fun navigateToDetails(bookId: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main_activity, BookDetailsFragment.newInstance(bookId))
            addToBackStack("BookDetails")
        }
    }
}