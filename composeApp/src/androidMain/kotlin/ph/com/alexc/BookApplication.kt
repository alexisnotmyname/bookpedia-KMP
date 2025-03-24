package ph.com.alexc

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ph.com.alexc.di.initKoin

class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}