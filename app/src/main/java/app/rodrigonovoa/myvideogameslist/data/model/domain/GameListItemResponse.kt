package app.rodrigonovoa.myvideogameslist.data.model.domain

data class GameListItemResponse(
    val id: Int, val name: String, val released: String, val metacritic: Int,
    val platforms: List<PlatformResponse>?, val genres: List<GenreDetailResponse>?,
    val background_image: String
) {

}