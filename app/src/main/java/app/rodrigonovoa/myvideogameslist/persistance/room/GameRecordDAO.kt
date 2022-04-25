package app.rodrigonovoa.myvideogameslist.persistance.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord

@Dao
interface GameRecordDAO {
    @Query("SELECT * from GameRecord")
    fun getAll(): List<GameRecord>

    @Query("SELECT * from GameRecord where gameid = :id")
    fun getRecordByGameId(id:Int): List<GameRecord>

    @Query("SELECT * from GameRecord where gamerecordid = :id")
    fun getRecordById(id:Int): GameRecord

    @Insert
    fun insert(gameRecord: GameRecord): Long
}