package com.victor.booktracker.ui.fragments.book_details

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
import com.victor.booktracker.databinding.BookDetailFragmentBinding
import com.victor.booktracker.ui.view_model.BookViewModel
import kotlinx.coroutines.launch

class BookDetailsFragment : Fragment(R.layout.book_detail_fragment) {
    private var _binding: BookDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookViewModel by activityViewModels()
    private val args: BookDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBookById(args.bookId)

        observeBookDetails()
        setupButtons()
    }

    private fun observeBookDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedBook.collect { book ->
                    book?.let {
                        binding.tvBookTitle.text = it.bookName
                        binding.tvBookAuthor.text = it.authorName ?: "N/A"
                        binding.tvBookCategory.text = it.category ?: "N/A"
                        binding.tvBookPages.text = "${it.lastPageRead ?: 0}/${it.totalOfPages}"
                        binding.tvBookDescription.text = it.description ?: "Sem descrição"
                        binding.etLastPage.setText((it.lastPageRead ?: 0).toString())
                        
                        binding.btFinish.isEnabled = !it.isBookFinished
                        if (it.isBookFinished) {
                            binding.btFinish.text = "Concluído"
                        }
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        binding.btEdit.setOnClickListener {
            val action = BookDetailsFragmentDirections.actionBookDetailsFragmentToAddBookFragment(args.bookId)
            findNavController().navigate(action)
        }

        binding.btFinish.setOnClickListener {
            viewModel.selectedBook.value?.let { book ->
                viewModel.finishBook(book)
            }
        }

        binding.btUpdateProgress.setOnClickListener {
            val lastPage = binding.etLastPage.text.toString().toIntOrNull() ?: 0
            viewModel.selectedBook.value?.let { book ->
                viewModel.updateLastPageRead(book, lastPage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}