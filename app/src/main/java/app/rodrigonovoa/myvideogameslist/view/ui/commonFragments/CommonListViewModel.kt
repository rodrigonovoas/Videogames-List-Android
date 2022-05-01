package app.rodrigonovoa.myvideogameslist.view.ui.commonFragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.GameListItemResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGameDetail
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class CommonListViewModel(private val repository: GamesListRepository, private val dateFormatterUtil: DateFormatterUtil): ViewModel() {
    private var _gameCompleteDates: List<String> = listOf()
    private var _pendingGames: List<PendingGameDetail> = listOf()
    private val _gamesList = MutableLiveData<GamesListResponse>().apply { postValue(null)}
    val gamesList: LiveData<GamesListResponse> get() = _gamesList

    fun setGameList(list: GamesListResponse){
        _gamesList.postValue(list)
    }

    fun getPendingGameList(): List<PendingGameDetail> {
        return _pendingGames
    }

    // TODO: this is the release date, not the complete date; retrieve from GameRecord
    private fun setGameCompleteDates(games:List<Game>){
        val datesList = mutableListOf<String>()

        games.forEach {
            datesList.add(dateFormatterUtil.fromTimeStampToDateString(it.released))
        }

        _gameCompleteDates = datesList.toList()
    }

    fun getGameCompleteDates(): List<String>{
        return _gameCompleteDates
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

    fun getPendingGamesFromLocalDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val pendingGames = getPendingGames().await()
            _pendingGames = pendingGames
            setGameList(GamesListResponse(0, "", "", null))
        }
    }

    suspend private fun getPendingGames() = CoroutineScope(Dispatchers.IO).async {
        return@async repository.getAllPendingGameWithDetail()
    }

    private fun mapGameToGamesListResponse(games:List<Game>): GamesListResponse{
        var gameResponseDetailList: MutableList<GameListItemResponse> = mutableListOf()

        games.forEach {
            gameResponseDetailList.add(
                GameListItemResponse(it.gameid!!, it.name, dateFormatterUtil.fromTimeStampToDateString(it.released), it.metacritic, listOf(), listOf(), it.image)
            )
        }

        setGameCompleteDates(games)

        return GamesListResponse(games.size, "", "", gameResponseDetailList.toList())
    }

}