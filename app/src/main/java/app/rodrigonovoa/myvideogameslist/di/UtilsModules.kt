package app.rodrigonovoa.myvideogameslist.di

import app.rodrigonovoa.myvideogameslist.persistance.sharedPreferences.Prefs
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import app.rodrigonovoa.myvideogameslist.utils.GlideUtils
import app.rodrigonovoa.myvideogameslist.utils.KoinUtils
import org.koin.dsl.module

val utilsModules = module {
    single { GlideUtils(get()) }
    single { DateFormatterUtil() }
    single { Prefs(get()) }
}
