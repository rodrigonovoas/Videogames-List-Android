package app.rodrigonovoa.myvideogameslist

import android.app.Application
import app.rodrigonovoa.myvideogameslist.utils.KoinUtils
import timber.log.Timber

class MyVideogamesListApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val koinUtils = KoinUtils(this@MyVideogamesListApp)
        koinUtils.setUpKoin()

        Timber.plant(Timber.DebugTree())
    }
}