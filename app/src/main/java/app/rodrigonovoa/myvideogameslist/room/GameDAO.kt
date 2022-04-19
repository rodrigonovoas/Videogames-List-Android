package app.rodrigonovoa.myvideogameslist.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.User

@Dao
interface GameDAO {
    @Query("SELECT * from Game")
    fun getAll(): List<Game>

    @Insert
    fun insert(game: Game)
}