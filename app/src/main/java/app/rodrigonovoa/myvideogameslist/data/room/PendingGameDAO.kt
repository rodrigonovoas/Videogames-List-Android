package app.rodrigonovoa.myvideogameslist.data.room

import androidx.room.Dao
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.data.model.localdb.PendingGame

@Dao
interface PendingGameDAO {
    @Query("SELECT * from PendingGame")
    fun getAll(): List<PendingGame>
}