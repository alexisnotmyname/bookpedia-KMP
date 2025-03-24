package ph.com.alexc.book.data.mappers

import ph.com.alexc.book.data.database.BookEntity
import ph.com.alexc.book.data.dto.SearchedBookDto
import ph.com.alexc.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishedYear = firstPublishedYear.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numberOfPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity() = BookEntity(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    languages = languages,
    authors = authors,
    firstPublishYear = firstPublishedYear,
    ratingsAverage = averageRating,
    ratingsCount = ratingsCount,
    numPagesMedian = numPages,
    numEditions = numEditions
)

fun BookEntity.toBook() = Book(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    languages = languages,
    authors = authors,
    firstPublishedYear = firstPublishYear,
    averageRating = ratingsAverage,
    ratingsCount = ratingsCount,
    numPages = numPagesMedian,
    numEditions = numEditions
)