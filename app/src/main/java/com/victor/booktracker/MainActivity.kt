package com.victor.booktracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.victor.booktracker.data.AppDatabase
import com.victor.booktracker.data.repository.OfflineBookRepository
import com.victor.booktracker.databinding.ActivityMainBinding
import com.victor.booktracker.ui.fragments.book_list.BookListFragment
import com.victor.booktracker.ui.view_model.BookViewModel
import com.victor.booktracker.ui.view_model.BookViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getInstance(this)
        val repository = OfflineBookRepository(db!!.bookDao())

        val viewModelFactory = BookViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[BookViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<BookListFragment>(R.id.fcv_main_activity)
            }
        }
    }
}