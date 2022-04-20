package app.rodrigonovoa.myvideogameslist.ui.gameDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(private val repository: GamesListRepository): ViewModel() {
    private val _retrievedGame = MutableLiveData<GameDetailResponse?>().apply { postValue(null)}
    val retrievedGame: LiveData<GameDetailResponse?> get() = _retrievedGame

    fun setRetrievedGame(game: GameDetailResponse){
        _retrievedGame.postValue(game)
    }

    fun getGameFromRepo(id: Int){
        viewModelScope.launch {
            repository.getGameById(id)
                .catch {
                    // error handling
                }
                .collect {
                    val game = it.body()
                    if(game != null){
                        setRetrievedGame(game)
                    }
                }
        }
    }

    fun getGameFromLocalDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
           val game =  repository.getGameByIdFromDb(id)
            Log.d("GAME_DETAIL", game.toString())
        }
    }

}