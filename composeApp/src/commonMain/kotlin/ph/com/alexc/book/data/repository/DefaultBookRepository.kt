package ph.com.alexc.book.data.repository

import androidx.sqlite.SQLiteException
import ph.com.alexc.book.data.database.FavoriteBookDao
import ph.com.alexc.book.data.mappers.toBook
import ph.com.alexc.book.data.mappers.toBookEntity
import ph.com.alexc.book.data.network.RemoteBookDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ph.com.alexc.book.domain.Book
import ph.com.alexc.book.domain.BookRepository
import ph.com.alexc.core.domain.DataError
import ph.com.alexc.core.domain.EmptyResult
import ph.com.alexc.core.domain.Result
import ph.com.alexc.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
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
        val localResult = favoriteBookDao.getFavoriteBook(bookWorkId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookWorkId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }

    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}