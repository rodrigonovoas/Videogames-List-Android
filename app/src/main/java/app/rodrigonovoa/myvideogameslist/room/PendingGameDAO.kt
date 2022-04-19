package app.rodrigonovoa.myvideogameslist.room

import androidx.room.Dao
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame

@Dao
interface PendingGameDAO {
    @Query("SELECT * from PendingGame")
    fun getAll(): List<PendingGame>
}