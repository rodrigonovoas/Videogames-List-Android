package app.rodrigonovoa.myvideogameslist.view.ui.addPendingGame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DatabaseUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPendingGameViewModel(private val repository: GamesListRepository, private val dbUtils: DatabaseUtils): ViewModel() {
    private val _recordInserted = MutableLiveData<Boolean?>().apply { postValue(false)}
    val recordInserted: LiveData<Boolean?> get() = _recordInserted

    suspend fun insertPendingGame(pending: PendingGame) = viewModelScope.launch(Dispatchers.IO) {
        val inserted = repository.insertPendingGame(pending)

        if (inserted > 0) _recordInserted.postValue(true)
    }

    fun insertGameInDb(gameDetailResponse: GameDetailResponse, notes: String, state: String){
        viewModelScope.launch(Dispatchers.IO) {
            val gameId = dbUtils.insertGame(gameDetailResponse).await()

            if(gameId > 0){
                val pending = PendingGame(null, gameDetailResponse.id, 1, notes, state)
                insertPendingGame(pending)
            }
        }
    }
}