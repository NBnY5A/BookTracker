package com.victor.booktracker.ui.fragments.add_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.victor.booktracker.R
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.databinding.AddBookFragmentBinding
import com.victor.booktracker.ui.view_model.BookViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class AddBookFragment : Fragment(R.layout.add_book_fragment) {
    private var _binding: AddBookFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookViewModel by activityViewModels()
    private val args: AddBookFragmentArgs by navArgs()
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddBookFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.bookId != -1) {
            isEditMode = true
            binding.btSaveBook.text = "Atualizar Livro"
            viewModel.getBookById(args.bookId)
            observeBookData()
        }

        binding.btSaveBook.setOnClickListener {
            saveBook()
        }
    }

    private fun observeBookData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedBook.collect { book ->
                    book?.let {
                        binding.etBookName.setText(it.bookName)
                        binding.etDescription.setText(it.description)
                        binding.etAuthorName.setText(it.authorName)
                        binding.etCategory.setText(it.category)
                        binding.etTotalPages.setText(it.totalOfPages.toString())
                        binding.etYearPublished.setText(it.yearOfPublished.toString())
                    }
                }
            }
        }
    }

    private fun saveBook() {
        val name = binding.etBookName.text.toString()
        val description = binding.etDescription.text.toString().ifBlank { null }
        val category = binding.etCategory.text.toString().ifBlank { null }
        val author = binding.etAuthorName.text.toString().ifBlank { null }
        val pages = binding.etTotalPages.text.toString().toIntOrNull() ?: 0
        val year = binding.etYearPublished.text.toString().toIntOrNull() ?: 0

        if (name.isNotBlank() && pages > 0) {
            val book = if (isEditMode) {
                viewModel.selectedBook.value?.copy(
                    bookName = name,
                    authorName = author,
                    description = description,
                    category = category,
                    totalOfPages = pages,
                    yearOfPublished = year
                )
            } else {
                BookEntity(
                    bookId = 0,
                    bookName = name,
                    authorName = author,
                    description = description,
                    category = category,
                    totalOfPages = pages,
                    yearOfPublished = year,
                    lastPageRead = 0,
                    isBookFinished = false
                )
            }

            book?.let {
                if (isEditMode) viewModel.updateBook(it) else viewModel.insertBook(it)
                findNavController().popBackStack()
            }
        } else {
            binding.etBookName.error = "Informe o nome e as páginas"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}