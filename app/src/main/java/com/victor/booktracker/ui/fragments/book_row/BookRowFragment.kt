package com.victor.booktracker.ui.fragments.book_row

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victor.booktracker.R
import com.victor.booktracker.databinding.BookRowFragmentBinding

class BookRowFragment : Fragment(R.layout.book_row_fragment) {
    private lateinit var bookRowFragmentBinding: BookRowFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookRowFragmentBinding = BookRowFragmentBinding.bind(view)

        bookRowFragmentBinding.tvTitle.text = "Meu Texto"
    }
}