package app.rodrigonovoa.myvideogameslist.model.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) val gameid: Int?,
    val name: String,
    val description: String,
    val released: Long,
    val publisher: String?,
    val platforms: String?,
    val esrbRating: String?,
    val metacritic: Int?,
    val website: String?,
    val image: String?
)
