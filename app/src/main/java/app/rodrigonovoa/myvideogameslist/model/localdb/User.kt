package app.rodrigonovoa.myvideogameslist.model.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true) val userid: Int?, val name: String)
