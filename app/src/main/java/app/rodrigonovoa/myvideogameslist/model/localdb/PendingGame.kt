package app.rodrigonovoa.myvideogameslist.model.localdb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Game::class,
    parentColumns = arrayOf("gameid"),
    childColumns = arrayOf("gameid"),
    onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("userid"),
        childColumns = arrayOf("userid"),
        onDelete = ForeignKey.CASCADE)
))

data class PendingGame(
    @PrimaryKey(autoGenerate = true) val pendinggameid: Int?,
    val gameid: Int,
    val userid: Int,
    val note: String,
    val state: String
)
