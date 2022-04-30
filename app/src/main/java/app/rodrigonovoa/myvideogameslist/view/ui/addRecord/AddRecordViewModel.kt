package app.rodrigonovoa.myvideogameslist.view.ui.addRecord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.EsrbRatingDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.PlatformResponse
import app.rodrigonovoa.myvideogameslist.model.domain.PublisherDetailResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DatabaseUtils
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddRecordViewModel(private val repository: GamesListRepository, private val dbUtils: DatabaseUtils): ViewModel() {
    private val _recordInserted = MutableLiveData<Boolean?>().apply { postValue(false)}
    val recordInserted: LiveData<Boolean?> get() = _recordInserted

    fun insertRecord(gameDetailResponse: GameDetailResponse, fromCalendar: Calendar,
                     toCalendar: Calendar, score: Int, notes: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var id = 0
            val game = dbUtils.checkIfGameExist(gameDetailResponse.id).await()

            if(game != null){
                id = game.gameid ?: 0
            }else{
                id = dbUtils.insertGame(gameDetailResponse).await().toInt()
            }

            val gameRecord = GameRecord(
                null,
                id,
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

}