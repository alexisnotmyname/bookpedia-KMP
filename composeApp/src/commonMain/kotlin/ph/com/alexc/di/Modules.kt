package ph.com.alexc.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ph.com.alexc.book.data.database.DatabaseFactory
import ph.com.alexc.book.data.database.FavoriteBookDatabase
import ph.com.alexc.book.data.network.KtorRemoteBookDataSource
import ph.com.alexc.book.data.network.RemoteBookDataSource
import ph.com.alexc.book.data.repository.DefaultBookRepository
import ph.com.alexc.book.domain.BookRepository
import ph.com.alexc.book.presentation.SelectedBookViewModel
import ph.com.alexc.book.presentation.book_detail.BookDetailViewModel
import ph.com.alexc.book.presentation.book_list.BookListViewModel
import ph.com.alexc.core.data.HttpClientFactory

expect val platformModule: Module

val sharedModule = module {
    single{ HttpClientFactory.create(get())  }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}