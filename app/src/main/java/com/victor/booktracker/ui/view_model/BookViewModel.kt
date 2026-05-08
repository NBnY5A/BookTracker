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

    private val _finishedBooks = MutableStateFlow<List<BookEntity>>(emptyList())
    val finishedBooks: StateFlow<List<BookEntity>> = _finishedBooks.asStateFlow()

    private val _selectedBook = MutableStateFlow<BookEntity?>(null)
    val selectedBook: StateFlow<BookEntity?> = _selectedBook.asStateFlow()

    init {
        getAllBooks()
        getFinishedBooks()
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            bookRepository.getAll().collect { booksList ->
                _books.value = booksList
            }
        }
    }

    private fun getFinishedBooks() {
        viewModelScope.launch {
            bookRepository.getFinishedBooks().collect { booksList ->
                _finishedBooks.value = booksList
            }
        }
    }

    fun insertBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.insert(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.update(book)
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

    fun updateLastPageRead(book: BookEntity, page: Int) {
        val updatedBook = book.copy(lastPageRead = page)
        updateBook(updatedBook)
    }

    fun finishBook(book: BookEntity) {
        val updatedBook = book.copy(isBookFinished = true)
        updateBook(updatedBook)
    }

}