package ph.com.alexc.book.data.source

import kotlinx.coroutines.flow.Flow
import ph.com.alexc.book.domain.Book

interface LocalSource {
    fun upsert(book: Book)
    fun getFavoriteBooks(): Flow<List<Book>>
    fun getFavoriteBook(id: String): Book?
    fun deleteFavoriteBook(id: String)
}