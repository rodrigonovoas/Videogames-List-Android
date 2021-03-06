package app.rodrigonovoa.myvideogameslist.persistance.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame

@Dao
interface GameDAO {
    @Query("SELECT * from Game")
    fun getAll(): List<Game>

    @Query("SELECT * from Game join GameRecord on Game.gameid = GameRecord.gameid")
    fun getAllFromGameRecord(): List<Game>

    @Insert
    fun insert(game: Game): Long

    @Query("SELECT * FROM Game WHERE gameid=:id ")
    fun getGameById(id: Int): Game

    @Delete
    fun delete(game: Game): Int

    @Query("DELETE FROM Game WHERE gameid=:id ")
    fun deleteById(id: Int): Int
}