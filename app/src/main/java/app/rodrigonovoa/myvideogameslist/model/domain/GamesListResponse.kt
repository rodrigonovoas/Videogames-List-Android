package app.rodrigonovoa.myvideogameslist.model.domain

data class GamesListResponse(val count: Int, val next:String, val previous: String, var results: List<GameListItemResponse>? = null)  {

}