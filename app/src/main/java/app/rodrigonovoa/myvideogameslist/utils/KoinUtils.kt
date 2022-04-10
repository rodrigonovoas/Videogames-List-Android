package app.rodrigonovoa.myvideogameslist.utils

import android.content.Context
import app.rodrigonovoa.myvideogameslist.di.netWorkModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class KoinUtils(val context: Context) {
    fun setUpKoin(){
        startKoin {
            androidContext(context)
            modules(netWorkModules)
        }
    }
}