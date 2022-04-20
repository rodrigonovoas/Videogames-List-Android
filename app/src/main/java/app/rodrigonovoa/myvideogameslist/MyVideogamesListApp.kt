package app.rodrigonovoa.myvideogameslist

import android.app.Application
import app.rodrigonovoa.myvideogameslist.model.localdb.User
import app.rodrigonovoa.myvideogameslist.room.UserDAO
import app.rodrigonovoa.myvideogameslist.utils.KoinUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MyVideogamesListApp: Application() {
    private val userDAO: UserDAO by inject()
    private val koinUtils: KoinUtils by inject()

    override fun onCreate() {
        super.onCreate()

        val koinUtils = KoinUtils(this@MyVideogamesListApp)
        koinUtils.setUpKoin()

        CoroutineScope(Dispatchers.IO).launch {
            userDAO.insert(User(null, "Rodrigod"))
        }
    }
}