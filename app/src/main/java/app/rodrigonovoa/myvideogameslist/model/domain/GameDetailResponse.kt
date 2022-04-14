package app.rodrigonovoa.myvideogameslist.model.domain

data class GameDetailResponse(
    val id: Int, val name: String, val description: String,
    val metacritic: Int, val released: String, val background_image_additional: String,
    val website: String
) {

}