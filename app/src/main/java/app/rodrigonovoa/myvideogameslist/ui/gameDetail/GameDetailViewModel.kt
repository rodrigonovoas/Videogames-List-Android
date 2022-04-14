package app.rodrigonovoa.myvideogameslist.ui.gameDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.network.ApiRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(private val repository:ApiRepository): ViewModel() {
    val retrievedGame = MutableLiveData<GameDetailResponse?>().apply { postValue(null)}

    fun getGameFromRepo(id: Int){
        viewModelScope.launch {
            repository.getGameById(id)
                .catch {
                    // error handling
                }
                .collect {
                    val game = it.body()
                    if(game != null){
                        retrievedGame.value = game
                    }
                }
        }
    }
}