package app.rodrigonovoa.myvideogameslist.model.localdb

data class PendingGameDetail(
    val pendinggameid: Int,
    val gameid: Int,
    val name: String,
    val state: String,
    val image: String
)