package ph.com.alexc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ph.com.alexc.app.App
import ph.com.alexc.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookpedia",
        ) {
            App()
        }
    }
}