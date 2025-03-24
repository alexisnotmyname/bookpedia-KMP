package ph.com.alexc.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ph.com.alexc.book.data.dto.SearchedBookDto

@Serializable
data class SearchResponseDto(
    @SerialName("docs") val results: List<SearchedBookDto>
)