package app.rodrigonovoa.myvideogameslist.ui.recordDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordDetailViewModel(private val repository: GamesListRepository): ViewModel() {
    private val _retrievedRecord = MutableLiveData<GameRecord?>().apply { postValue(null)}
    val retrievedRecord: LiveData<GameRecord?> get() = _retrievedRecord

    fun getGameFromLocalDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val record =  repository.getGameRecordsById(id)

            if(record != null){
                _retrievedRecord.postValue(record)
            }
        }
    }
}