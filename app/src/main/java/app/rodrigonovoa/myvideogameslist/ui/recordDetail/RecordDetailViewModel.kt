package app.rodrigonovoa.myvideogameslist.ui.recordDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordDetailViewModel(private val repository: GamesListRepository): ViewModel() {
    private val _retrievedRecord = MutableLiveData<GameRecord?>().apply { postValue(null)}
    val retrievedRecord: LiveData<GameRecord?> get() = _retrievedRecord

    private val _retrievedGame = MutableLiveData<Game?>().apply { postValue(null)}
    val retrievedGame: LiveData<Game?> get() = _retrievedGame

    fun getRecordFromLocalDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val record =  repository.getGameRecordsById(id)

            if(record != null){
                getGameDetail(record.gameid)
                _retrievedRecord.postValue(record)
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