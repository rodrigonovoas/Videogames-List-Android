package app.rodrigonovoa.myvideogameslist.di

import android.app.Application
import androidx.room.Room
import app.rodrigonovoa.myvideogameslist.room.GameDAO
import app.rodrigonovoa.myvideogameslist.room.GamesListDb
import app.rodrigonovoa.myvideogameslist.room.UserDAO
import org.koin.dsl.module

val roomModules = module {
    single { provideDataBase(get()) }
    single { provideUserDAO(get()) }
    single { provideGameDAO(get()) }
}

fun provideDataBase(application: Application): GamesListDb {
    return Room.databaseBuilder(application, GamesListDb::class.java, "gameslist_database")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideUserDAO(dataBase: GamesListDb): UserDAO {
    return dataBase.userDao()
}

fun provideGameDAO(dataBase: GamesListDb): GameDAO {
    return dataBase.gameDao()
}