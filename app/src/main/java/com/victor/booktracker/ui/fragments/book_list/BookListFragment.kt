package com.victor.booktracker.ui.fragments.book_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.booktracker.R
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.databinding.BookListFragmentBinding
import com.victor.booktracker.ui.adapters.BookRowAdapter
import com.victor.booktracker.ui.view_model.BookViewModel
import kotlinx.coroutines.launch

class BookListFragment : Fragment(R.layout.book_list_fragment) {
    private var _binding: BookListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookRowAdapter: BookRowAdapter

    private val viewModel: BookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFab()
        observeBooks()
    }

    private fun setupRecyclerView() {
        bookRowAdapter = BookRowAdapter(
            onBookClicked = { book -> navigateToDetails(book.bookId) },
            onEditClicked = { book -> navigateToEdit(book.bookId) },
            onDeleteClicked = { book -> showDeleteConfirmation(book) }
        )

        binding.rvBooksList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooksList.adapter = bookRowAdapter
    }

    private fun showDeleteConfirmation(book: BookEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Livro")
            .setMessage("Tem certeza que deseja excluir o livro \"${book.bookName}\"?")
            .setPositiveButton("Excluir") { _, _ ->
                viewModel.deleteBook(book)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun navigateToEdit(bookId: Int) {
        val action = BookListFragmentDirections.actionBookListFragmentToAddBookFragment(bookId)
        findNavController().navigate(action)
    }

    private fun setupFab() {
        binding.fabAddBook.setOnClickListener {
            val action = BookListFragmentDirections.actionBookListFragmentToAddBookFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeBooks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.books.collect { bookList ->
                    bookRowAdapter.submitList(bookList)
                    binding.tvEmptyState.visibility = if (bookList.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun navigateToDetails(bookId: Int) {
        val action = BookListFragmentDirections.actionBookListFragmentToBookDetailsFragment(bookId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}