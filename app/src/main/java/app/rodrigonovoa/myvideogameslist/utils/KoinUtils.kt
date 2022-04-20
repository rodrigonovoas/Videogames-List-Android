package app.rodrigonovoa.myvideogameslist.utils

import android.content.Context
import app.rodrigonovoa.myvideogameslist.di.netWorkModules
import app.rodrigonovoa.myvideogameslist.di.roomModules
import app.rodrigonovoa.myvideogameslist.di.utilsModules
import app.rodrigonovoa.myvideogameslist.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class KoinUtils(val context: Context) {
    fun setUpKoin(){
        startKoin {
            androidContext(context)
            modules(listOf(netWorkModules, viewModelModules, roomModules, utilsModules))
        }
    }
}