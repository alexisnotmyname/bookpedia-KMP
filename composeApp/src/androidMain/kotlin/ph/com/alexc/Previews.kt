package ph.com.alexc

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ph.com.alexc.book.domain.Book
import ph.com.alexc.book.presentation.book_list.BookListScreen
import ph.com.alexc.book.presentation.book_list.BookListState
import ph.com.alexc.book.presentation.book_list.components.BookSearchBar

@Preview(showBackground = true)
@Composable
private fun BookSearchPreview() {
    BookSearchBar(
        searchQuery = "",
        onSearchQueryChange = {},
        onImeSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}
    )
}

private val books = (1 .. 100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
//        imageUrl = "https://picsum.photos/200/300?random=$it",
        authors = listOf("Author $it"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishedYear = null,
        averageRating = 4.675,
        ratingsCount = 5,
        numPages = 115,
        numEditions = 3

    )
}