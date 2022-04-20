package app.rodrigonovoa.myvideogameslist.data.model.domain

data class GamesListResponse(val count: Int, val next:String, val previous: String, var results: List<GameResponse>? = null)  {

}