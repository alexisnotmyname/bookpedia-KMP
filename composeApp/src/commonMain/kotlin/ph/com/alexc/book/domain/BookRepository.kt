package ph.com.alexc.book.domain

import ph.com.alexc.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow
import ph.com.alexc.core.domain.DataError
import ph.com.alexc.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String, resultLimit: Int? = null): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)

}