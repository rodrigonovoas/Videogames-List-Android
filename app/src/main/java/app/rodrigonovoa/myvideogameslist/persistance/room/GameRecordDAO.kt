package app.rodrigonovoa.myvideogameslist.persistance.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame

@Dao
interface GameRecordDAO {
    @Query("SELECT * from GameRecord")
    fun getAll(): List<GameRecord>

    @Query("SELECT * from GameRecord where gameid = :id")
    fun getRecordByGameId(id:Int): List<GameRecord>

    @Query("SELECT * from GameRecord where gamerecordid = :id")
    fun getRecordById(id:Int): GameRecord

    @Query("SELECT enddate from GameRecord where gameid = :id")
    fun getFinishDateByGameId(id:Int): Long

    @Insert
    fun insert(gameRecord: GameRecord): Long

    @Delete
    fun delete(gameRecord: GameRecord): Int
}