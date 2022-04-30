package app.rodrigonovoa.myvideogameslist.utils

import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.EsrbRatingDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.PlatformResponse
import app.rodrigonovoa.myvideogameslist.model.domain.PublisherDetailResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.*

class DatabaseUtils(private val repository: GamesListRepository, private val dateFormatterUtil: DateFormatterUtil) {
    suspend fun insertGame(gameDetailResponse: GameDetailResponse) =
        CoroutineScope(Dispatchers.Default).async {
            val game = Game(
                gameDetailResponse.id,
                gameDetailResponse.name,
                gameDetailResponse.description,
                dateFormatterUtil.fromDateStringToTimeStamp(
                    gameDetailResponse.released
                ),
                getPublishersAsString(gameDetailResponse.publishers),
                getPlatformsAsString(gameDetailResponse.platforms),
                getEsrbRatingAsString(gameDetailResponse.esrb_rating),
                gameDetailResponse.metacritic,
                gameDetailResponse.website,
                gameDetailResponse.background_image_additional
            )

            val insertGame = async { repository.insertGame(game) }

            return@async insertGame.await()
        }

    private fun getEsrbRatingAsString(rating: EsrbRatingDetailResponse?): String {
        if(rating != null){
            return rating.name
        }else{
            return ""
        }
    }

    private fun getPlatformsAsString(platforms: List<PlatformResponse>?): String{
        var platformsAsString: String = ""

        if(platforms != null){
            platforms.forEach {
                if(it != null){
                    if(it.platform != null){
                        if(platformsAsString.isEmpty()){
                            platformsAsString += it.platform.name
                        }else{
                            platformsAsString += ", " + it.platform.name
                        }
                    }
                }
            }
        }

        return platformsAsString
    }

    private fun getPublishersAsString(publishers: List<PublisherDetailResponse>?): String {
        var publishersAsString: String = ""

        if(publishers != null){
            publishers.forEach {
                if(it != null){
                    if(publishersAsString.isEmpty()){
                        publishersAsString += it.name
                    }else{
                        publishersAsString += ", " + it.name
                    }
                }
            }
        }

        return publishersAsString
    }
}