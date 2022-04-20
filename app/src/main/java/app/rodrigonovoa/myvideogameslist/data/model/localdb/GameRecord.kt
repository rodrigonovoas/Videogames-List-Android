package app.rodrigonovoa.myvideogameslist.data.model.localdb

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

data class GameRecord(
    @PrimaryKey(autoGenerate = true) val gamerecordid: Int?,
    val gameid: Int,
    val userid: Int,
    val initdate: Long,
    val enddate: Long,
    val score: Int,
    val notes: String
)
