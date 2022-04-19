package app.rodrigonovoa.myvideogameslist.room

import androidx.room.Dao
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord

@Dao
interface GameRecordDAO {
    @Query("SELECT * from GameRecord")
    fun getAll(): List<GameRecord>
}