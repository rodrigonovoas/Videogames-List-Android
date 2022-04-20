package app.rodrigonovoa.myvideogameslist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord

@Dao
interface GameRecordDAO {
    @Query("SELECT * from GameRecord")
    fun getAll(): List<GameRecord>

    @Insert
    fun insert(gameRecord: GameRecord): Long
}