package app.rodrigonovoa.myvideogameslist.ui.commonFragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.data.room.GameDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommonListViewModel(private val repository: GamesListRepository, private val gamesDAO: GameDAO): ViewModel() {
    val gamesList = MutableLiveData<GamesListResponse?>().apply { postValue(null)}

    @InternalCoroutinesApi
    fun getGamesFromRepo(){
        viewModelScope.launch {
            repository.getGamesListFromRepository()
                .catch {
                    // error handling
                }
                .collect {
                    val list = it.body()
                    if(list != null){
                        gamesList.value = list
                    }
                }
        }
    }

    fun getGamesFromLocalDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val games = repository.getAllGamesFromDb()

            if (games.size > 0) {
                gamesList.postValue(mapGameToGamesListResponse(games))
            }
        }
    }

    private fun mapGameToGamesListResponse(games:List<Game>): GamesListResponse{
        var gameResponseDetailList: MutableList<GameResponse> = mutableListOf()
        games.forEach {
            gameResponseDetailList.add(
                GameResponse(it.gameid!!, it.name, "", it.metacritic, it.image)
            )
        }

        return GamesListResponse(games.size, "", "", gameResponseDetailList.toList())
    }

}