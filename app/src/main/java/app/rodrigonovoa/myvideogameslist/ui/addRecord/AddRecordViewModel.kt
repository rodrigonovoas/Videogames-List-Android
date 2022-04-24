package app.rodrigonovoa.myvideogameslist.ui.addRecord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.domain.EsrbRatingDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.PlatformResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.PublisherDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddRecordViewModel(private val repository: GamesListRepository, private val dateFormatterUtil: DateFormatterUtil): ViewModel() {
    private val _recordInserted = MutableLiveData<Boolean?>().apply { postValue(false)}
    val recordInserted: LiveData<Boolean?> get() = _recordInserted

    fun insertRecord(gameDetailResponse: GameDetailResponse, fromCalendar: Calendar,
                     toCalendar: Calendar, score: Int, notes: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

            val insertedGameId = repository.insertGame(game)

            val gameRecord = GameRecord(
                null,
                insertedGameId.toInt(),
                1,
                fromCalendar.timeInMillis,
                toCalendar.timeInMillis,
                score,
                notes
            )

            val insertedRecordId = repository.insertGameRecord(gameRecord)

            if (insertedRecordId > 0) {
                _recordInserted.postValue(true)
            }
        }
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