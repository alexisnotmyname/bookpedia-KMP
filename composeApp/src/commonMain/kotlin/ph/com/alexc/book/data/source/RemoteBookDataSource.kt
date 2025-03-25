package ph.com.alexc.book.data.source

import ph.com.alexc.book.data.dto.BookWorkDto
import ph.com.alexc.book.data.dto.SearchResponseDto
import ph.com.alexc.core.domain.DataError
import ph.com.alexc.core.domain.Result


interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}