package com.victor.booktracker.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    val bookId: Int,
    @ColumnInfo(name = "book_name")
    val bookName: String,
    @ColumnInfo(name = "author_name")
    val authorName: String?,
    val description: String?,
    @ColumnInfo(name = "last_page_read")
    val lastPageRead: Int?,
    @ColumnInfo(name = "is_book_finished", defaultValue = "false")
    val isBookFinished: Boolean
)
