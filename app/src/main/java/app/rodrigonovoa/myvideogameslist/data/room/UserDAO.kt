package app.rodrigonovoa.myvideogameslist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.data.model.localdb.User

@Dao
interface UserDAO {
    @Query("SELECT * from User")
    fun getAll(): List<User>

    @Query("SELECT * from User where userid = 1")
    fun getFirstUSer(): User

    @Insert
    fun insert(user: User)
}