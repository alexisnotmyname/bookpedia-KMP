package ph.com.alexc.book.data.source.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ph.com.alexc.BookpediaDatabase
import ph.com.alexc.book.data.source.LocalSource
import ph.com.alexc.book.domain.Book

class LocalDataSource(
    database: BookpediaDatabase
): LocalSource {
    private val query = database.bookpediaDatabaseQueries

    override fun upsert(book: Book) {
        query.transaction {
            query.insertBookToFavorites(
                id = book.id,
                title = book.title,
                description = book.description,
                imageUrl = book.imageUrl,
                languages = book.languages.toJson(),
                authors = book.authors.toJson(),
                firstPublishYear = book.firstPublishedYear,
                ratingsAverage = book.averageRating,
                ratingsCount = book.ratingsCount?.toLong(),
                numPagesMedian = book.numPages?.toLong(),
                numEditions = book.numEditions.toLong()
            )
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return query.getFavoriteBooks()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { books ->
                books.map { row ->
                    Book(
                        id = row.id,
                        title = row.title,
                        description = row.description,
                        imageUrl = row.imageUrl,
                        languages = row.languages.toList(),
                        authors = row.authors.toList(),
                        firstPublishedYear = row.firstPublishYear,
                        averageRating = row.ratingsAverage,
                        ratingsCount = row.ratingsCount?.toInt(),
                        numPages = row.numPagesMedian?.toInt(),
                        numEditions = row.numEditions.toInt(),
                    )
                }
            }
    }

    override fun getFavoriteBook(id: String): Book? {
        return query.getFavoriteBook(id)
            .executeAsOneOrNull()?.let {
                Book(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    languages = it.languages.toList(),
                    authors = it.authors.toList(),
                    firstPublishedYear = it.firstPublishYear,
                    averageRating = it.ratingsAverage,
                    ratingsCount = it.ratingsCount?.toInt(),
                    numPages = it.numPagesMedian?.toInt(),
                    numEditions = it.numEditions.toInt(),
                )
            }
    }

    override fun deleteFavoriteBook(id: String) {
        query.deleteFavoriteBook(id)
    }
}

fun List<String>.toJson(): String = Json.encodeToString(this)
fun String.toList(): List<String> = Json.decodeFromString(this)