package app.rodrigonovoa.myvideogameslist.di

import app.rodrigonovoa.myvideogameslist.utils.GlideUtils
import app.rodrigonovoa.myvideogameslist.utils.KoinUtils
import org.koin.dsl.module

val utilsModules = module {
    single { GlideUtils(get()) }
    single { KoinUtils(get()) }
}
