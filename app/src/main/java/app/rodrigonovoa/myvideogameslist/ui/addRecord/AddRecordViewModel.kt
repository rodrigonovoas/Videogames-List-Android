package app.rodrigonovoa.myvideogameslist.ui.addRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddRecordViewModel(private val repository: GamesListRepository, private val dateUtils: DateUtils): ViewModel() {

    fun insertRecord(gameDetailResponse: GameDetailResponse, fromCalendar: Calendar,
                     toCalendar: Calendar, score: Int, notes: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val game = Game(
                null,
                gameDetailResponse.name,
                gameDetailResponse.description,
                dateUtils.fromDateStringToTimeStamp(
                    gameDetailResponse.released
                ),
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

            }
        }
    }

}