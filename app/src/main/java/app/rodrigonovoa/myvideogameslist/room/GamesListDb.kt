package app.rodrigonovoa.myvideogameslist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame
import app.rodrigonovoa.myvideogameslist.model.localdb.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Game::class, GameRecord::class, PendingGame::class], version = 1)
abstract class GamesListDb : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun gameDao(): GameDAO
    abstract fun gameRecordDao(): GameRecordDAO
    abstract fun pendingGameDao(): PendingGameDAO

    /*
    companion object {
        private var instance: GamesListDb? = null

        @Synchronized
        fun getInstance(context: Context): GamesListDb {
            if(instance == null)
                instance = Room.databaseBuilder(context.applicationContext, GamesListDb::class.java,
                    "gameslist_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: GamesListDb) {
            val userDAO = db.userDao()
            CoroutineScope(Dispatchers.IO).launch{
                userDAO.insert(User(null, "Rodri"))
                userDAO.insert(User(null, "Gabriel"))
            }
        }
    }
    */
}