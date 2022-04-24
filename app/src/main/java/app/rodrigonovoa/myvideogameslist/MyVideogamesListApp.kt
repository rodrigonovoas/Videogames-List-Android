package app.rodrigonovoa.myvideogameslist

import android.app.Application
import app.rodrigonovoa.myvideogameslist.data.model.localdb.User
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.KoinUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MyVideogamesListApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val koinUtils = KoinUtils(this@MyVideogamesListApp)
        koinUtils.setUpKoin()
    }
}