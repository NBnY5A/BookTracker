package com.victor.booktracker.ui.fragments.add_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.victor.booktracker.R
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.databinding.AddBookFragmentBinding
import com.victor.booktracker.ui.view_model.BookViewModel

class AddBookFragment : Fragment(R.layout.add_book_fragment) {
    private var _binding: AddBookFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookViewModel by activityViewModels()

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

        binding.btSaveBook.setOnClickListener {
            saveBook()
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
            val newBook = BookEntity(
                bookId = 0,
                bookName = name,
                authorName = author,
                description = description,
                category = category,
                totalOfPages = pages,
                yearOfPublished = year,
                lastPageRead = null,
                isBookFinished = false
            )

            viewModel.insertBook(newBook)

            requireActivity().supportFragmentManager.popBackStack()
        } else {
            binding.etBookName.error = "Informe o nome e as páginas"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}