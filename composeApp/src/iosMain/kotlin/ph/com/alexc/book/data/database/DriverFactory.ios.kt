package ph.com.alexc.book.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ph.com.alexc.BookpediaDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = BookpediaDatabase.Schema,
            name = "book.db"
        )
    }

}