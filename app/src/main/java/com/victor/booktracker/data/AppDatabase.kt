package com.victor.booktracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victor.booktracker.data.dao.BookDao
import com.victor.booktracker.data.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "books_database"
                ).build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}