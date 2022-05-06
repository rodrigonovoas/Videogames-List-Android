package app.rodrigonovoa.myvideogameslist.view.ui.recordDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.DBUtil
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DatabaseUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordDetailViewModel(private val repository: GamesListRepository, private val dbUtils: DatabaseUtils): ViewModel() {
    private val _retrievedRecord = MutableLiveData<GameRecord?>().apply { postValue(null)}
    val retrievedRecord: LiveData<GameRecord?> get() = _retrievedRecord

    private val _retrievedGame = MutableLiveData<Game?>().apply { postValue(null)}
    val retrievedGame: LiveData<Game?> get() = _retrievedGame

    private val _recordDeleted = MutableLiveData<Boolean>().apply { postValue(false)}
    val recordDeleted: LiveData<Boolean> get() = _recordDeleted

    fun getRecordFromLocalDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val record =  repository.getGameRecordsByGameId(id)

            if(record != null && record.isNotEmpty()){
                getGameDetail(id)
                _retrievedRecord.postValue(record[0])
            }
        }
    }

    fun deleteRecordFromDb(gameRecord: GameRecord){
        if(repository != null){
            CoroutineScope(Dispatchers.IO).launch {
                val isDeleted = repository.deleteGameRecord(gameRecord)

                if(isDeleted == 1){
                    val gameExists = dbUtils.checkIfGameExistInPendingList(gameRecord.gameid).await()

                    if(gameExists.isEmpty()){
                       val gameDeleted = repository.deleteGameById(gameRecord.gameid)

                        if(gameDeleted == 1){
                            _recordDeleted.postValue(true)
                        }
                    }else{
                        _recordDeleted.postValue(true)
                    }
                }
            }
        }
    }

    private fun getGameDetail(id: Int ){
        viewModelScope.launch(Dispatchers.IO) {
            val game = repository.getGameByIdFromDb(id)

            if(game != null){
                _retrievedGame.postValue(game)
            }
        }
    }
}