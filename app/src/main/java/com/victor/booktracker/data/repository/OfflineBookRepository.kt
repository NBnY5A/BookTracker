package com.victor.booktracker.data.repository

import com.victor.booktracker.data.dao.BookDao
import com.victor.booktracker.data.entity.BookEntity
import kotlinx.coroutines.flow.Flow

class OfflineBookRepository(private val bookDao: BookDao) : BookRepository {
    override suspend fun insert(bookEntity: BookEntity) = bookDao.insertBook(bookEntity)

    override suspend fun update(bookEntity: BookEntity) = bookDao.updateBook(bookEntity)

    override suspend fun delete(bookEntity: BookEntity) = bookDao.deleteBook(bookEntity)

    override fun getAll(): Flow<List<BookEntity>> = bookDao.getAllBooks()

    override suspend fun getBookById(id: Int): BookEntity? = bookDao.getBookById(id)
}