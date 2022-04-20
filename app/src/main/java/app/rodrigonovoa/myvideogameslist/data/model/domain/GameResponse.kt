package app.rodrigonovoa.myvideogameslist.data.model.domain

data class GameResponse(
    val id: Int, val name: String, val released: String, val metacritic: Int,
    val platforms: List<PlatformResponse>?, val genres: List<GenreResponse>?,
    val background_image: String
) {

}