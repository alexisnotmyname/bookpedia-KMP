package ph.com.alexc.di


import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ph.com.alexc.BookpediaDatabase
import ph.com.alexc.book.data.source.impl.KtorRemoteBookDataSource
import ph.com.alexc.book.data.source.RemoteBookDataSource
import ph.com.alexc.book.data.repository.DefaultBookRepository
import ph.com.alexc.book.data.source.LocalSource
import ph.com.alexc.book.data.source.impl.LocalDataSource
import ph.com.alexc.book.domain.BookRepository
import ph.com.alexc.book.presentation.SelectedBookViewModel
import ph.com.alexc.book.presentation.book_detail.BookDetailViewModel
import ph.com.alexc.book.presentation.book_list.BookListViewModel
import ph.com.alexc.core.data.HttpClientFactory

expect val platformModule: Module

val sharedModule = module {
    single{ HttpClientFactory.create(get())  }
    single<BookpediaDatabase> { BookpediaDatabase(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::LocalDataSource).bind<LocalSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}