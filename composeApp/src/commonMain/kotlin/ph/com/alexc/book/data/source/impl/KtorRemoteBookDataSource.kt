package ph.com.alexc.book.data.source.impl

import ph.com.alexc.book.data.dto.BookWorkDto
import ph.com.alexc.book.data.dto.SearchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ph.com.alexc.book.data.source.RemoteBookDataSource
import ph.com.alexc.core.data.safeCall
import ph.com.alexc.core.domain.DataError
import ph.com.alexc.core.domain.Result

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(urlString = "$BASE_URL/search.json") {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(urlString = "$BASE_URL/works/$bookWorkId.json")
        }
    }
}