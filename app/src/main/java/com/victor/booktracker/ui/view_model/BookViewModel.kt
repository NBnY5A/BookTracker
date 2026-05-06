package com.victor.booktracker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.booktracker.data.entity.BookEntity
import com.victor.booktracker.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    private val _books = MutableStateFlow<List<BookEntity>>(emptyList())
    val books: StateFlow<List<BookEntity>> = _books.asStateFlow()

    private val _selectedBook = MutableStateFlow<BookEntity?>(null)
    val selectedBook: StateFlow<BookEntity?> = _selectedBook.asStateFlow()

    init {
        getAllBooks()
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            bookRepository.getAll().collect { booksList ->
                _books.value = booksList
            }
        }
    }

    fun insertBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.insert(book)
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.delete(book)
        }
    }

    fun getBookById(id: Int) {
        viewModelScope.launch {
            _selectedBook.value = bookRepository.getBookById(id)
        }
    }

}