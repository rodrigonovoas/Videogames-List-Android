package app.rodrigonovoa.myvideogameslist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game

@Dao
interface GameDAO {
    @Query("SELECT * from Game")
    fun getAll(): List<Game>

    @Insert
    fun insert(game: Game): Long

    @Query("SELECT * FROM Game WHERE gameid=:id ")
    fun getGameById(id: Int): Game
}