package ph.com.alexc.book.data.repository

import ph.com.alexc.book.data.mappers.toBook
import ph.com.alexc.book.data.source.RemoteBookDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ph.com.alexc.book.data.source.impl.LocalDataSource
import ph.com.alexc.book.domain.Book
import ph.com.alexc.book.domain.BookRepository
import ph.com.alexc.core.domain.DataError
import ph.com.alexc.core.domain.EmptyResult
import ph.com.alexc.core.domain.Result
import ph.com.alexc.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val localDataSource: LocalDataSource
): BookRepository {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query, resultLimit)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
        val localResult = localDataSource.getFavoriteBook(bookWorkId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookWorkId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }

    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return localDataSource.getFavoriteBooks()
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return localDataSource.getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            localDataSource.upsert(book)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        localDataSource.deleteFavoriteBook(id)
    }
}