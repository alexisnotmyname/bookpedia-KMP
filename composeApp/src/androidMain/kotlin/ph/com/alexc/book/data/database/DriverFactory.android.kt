package ph.com.alexc.book.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ph.com.alexc.BookpediaDatabase

actual class DriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = BookpediaDatabase.Schema,
            context = context,
            name = "book.db"
        )
    }

}