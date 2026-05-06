package com.victor.booktracker.data.repository

import com.victor.booktracker.data.entity.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun insert(bookEntity: BookEntity)

    suspend fun update(bookEntity: BookEntity)

    suspend fun delete(bookEntity: BookEntity)

    fun getAll(): Flow<List<BookEntity>>

    suspend fun getBookById(id: Int): BookEntity?
}