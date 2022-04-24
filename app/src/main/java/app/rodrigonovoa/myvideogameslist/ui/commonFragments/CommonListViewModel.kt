package app.rodrigonovoa.myvideogameslist.ui.commonFragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameListItemResponse
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
    private val _gamesList = MutableLiveData<GamesListResponse>().apply { postValue(null)}
    val gamesList: LiveData<GamesListResponse> get() = _gamesList

    fun setGameList(list: GamesListResponse){
        _gamesList.postValue(list)
    }

    @InternalCoroutinesApi
    fun getGamesFromRepo(){
        viewModelScope.launch {
            repository.getGamesListFromRepository()
                .catch {
                    Log.d("COMMON_LIST","error")
                    // error handling
                }
                .collect {
                    val list = it.body()
                    if(list != null){
                        setGameList(list)
                    }
                }
        }
    }

    fun getGamesFromLocalDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val games = repository.getAllGamesFromDb()

            if (games.size > 0) {
                setGameList(mapGameToGamesListResponse(games))
            }
        }
    }

    private fun mapGameToGamesListResponse(games:List<Game>): GamesListResponse{
        var gameResponseDetailList: MutableList<GameListItemResponse> = mutableListOf()

        games.forEach {
            gameResponseDetailList.add(
                GameListItemResponse(it.gameid!!, it.name, "21-04-2022", it.metacritic, listOf(), listOf(), it.image)
            )
        }

        return GamesListResponse(games.size, "", "", gameResponseDetailList.toList())
    }

}