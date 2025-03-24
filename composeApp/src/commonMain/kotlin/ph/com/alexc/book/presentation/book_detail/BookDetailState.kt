package ph.com.alexc.book.presentation.book_detail

import ph.com.alexc.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null,
)
