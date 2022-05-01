package app.rodrigonovoa.myvideogameslist.persistance.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGameDetail

@Dao
interface PendingGameDAO {
    @Query("SELECT * from PendingGame")
    fun getAll(): List<PendingGame>

    @Query("SELECT PendingGame.pendinggameid, PendingGame.state, PendingGame.addeddate, Game.gameid, Game.name, Game.image from PendingGame left join Game on PendingGame.gameid = Game.gameid")
    fun getAllWithDetail(): List<PendingGameDetail>

    @Query("SELECT * from PendingGame where gameid = :id")
    fun getPendingByGameId(id:Int): List<PendingGame>

    @Query("SELECT * from PendingGame where pendinggameid = :id")
    fun getPendingById(id:Int): PendingGame

    @Insert
    fun insert(pending: PendingGame): Long
}