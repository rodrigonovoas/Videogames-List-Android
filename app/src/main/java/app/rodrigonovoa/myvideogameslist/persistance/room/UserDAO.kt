package app.rodrigonovoa.myvideogameslist.persistance.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.rodrigonovoa.myvideogameslist.model.localdb.User

@Dao
interface UserDAO {
    @Query("SELECT * from User")
    fun getAll(): List<User>

    @Query("SELECT * from User where userid = 1")
    fun getFirstUSer(): User

    @Query("UPDATE User set name = :username where userid = :id")
    fun updateUsername(username: String, id: Int): Int

    @Insert
    fun insert(user: User)
}