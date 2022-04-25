package app.rodrigonovoa.myvideogameslist.model.domain

import java.io.Serializable

data class GameDetailResponse(
    val id: Int, val name: String, val description: String,
    val metacritic: Int, val released: String,
    val background_image_additional: String, val website: String,
    val publishers: List<PublisherDetailResponse>?, val esrb_rating: EsrbRatingDetailResponse?,
    val platforms: List<PlatformResponse>?
): Serializable{

}