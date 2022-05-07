package app.rodrigonovoa.myvideogameslist.view.ui.commonFragments

import android.content.Context
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
import app.rodrigonovoa.myvideogameslist.utils.DatabaseUtils
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class CommonListViewModel(
    private val repository: GamesListRepository,
    private val dateFormatterUtil: DateFormatterUtil,
    private val dbUtils: DatabaseUtils
) : ViewModel() {
    private var _gameCompleteDates: List<String> = listOf()
    private var _pendingGames: List<PendingGameDetail> = listOf()
    private val _gamesList = MutableLiveData<GamesListResponse?>().apply { postValue(null)}
    val gamesList: LiveData<GamesListResponse?> get() = _gamesList

    fun setGameList(list: GamesListResponse?){
        _gamesList.postValue(list)
    }

    fun getPendingGameList(): List<PendingGameDetail> {
        return _pendingGames
    }

    private fun setGameCompleteDates(games:List<Game>, response: GamesListResponse){
        viewModelScope.launch(Dispatchers.IO) {
            val datesList = mutableListOf<String>()

            games.forEach {
                if(it.gameid != null){
                    val endDate = repository.getFinishDateByGameId(it.gameid)
                    datesList.add(dateFormatterUtil.fromTimeStampToDateString(endDate))
                }
            }

            _gameCompleteDates = datesList.toList()
            setGameList(response)
        }
    }

    fun getGameCompleteDates(): List<String>{
        return _gameCompleteDates
    }

    @InternalCoroutinesApi
    fun getGamesByQueryFromRepo(query: String){
        viewModelScope.launch {
            repository.getGamesByQuery(query)
                .catch {
                    Log.d("COMMON_LIST","error")
                    // error handling
                }
                .collect {
                    val list = it.body()
                    setGameList(list)
                }
        }
    }

    fun getGamesInGameRecordsFromLocalDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val games = repository.getAllGamesInGameRecorFromDb()

            if (games.isNotEmpty()) {
                mapGameToGamesListResponse(games)
            }else{
                setGameList(null)
            }
        }
    }

    fun getPendingGamesFromLocalDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val pendingGames = getPendingGames().await()
            _pendingGames = pendingGames
            setGameList(null)
        }
    }

    suspend private fun getPendingGames() = CoroutineScope(Dispatchers.IO).async {
        return@async repository.getAllPendingGameWithDetail()
    }

    fun deletePendingGame(item: PendingGameDetail){
        if(repository != null){
            val pendingGame = PendingGame(item.pendinggameid, item.gameid, 1, "", item.state, item.addeddate)
            CoroutineScope(Dispatchers.IO).launch {
                val isDeleted = repository.deletePendingGame(pendingGame)

                if(isDeleted == 1){
                    val gameExists = dbUtils.checkIfGameExistInRecordsList(item.gameid).await()

                    if(gameExists.isEmpty()){
                        repository.deleteGameById(item.gameid)
                    }
                    getPendingGamesFromLocalDb()
                }
            }
        }
    }

    private fun mapGameToGamesListResponse(games:List<Game>){
        var gameResponseDetailList: MutableList<GameListItemResponse> = mutableListOf()

        games.forEach {
            gameResponseDetailList.add(
                GameListItemResponse(it.gameid!!, it.name, dateFormatterUtil.fromTimeStampToDateString(it.released), it.metacritic, listOf(), listOf(), it.image)
            )
        }

        setGameCompleteDates(games, GamesListResponse(games.size, "", "", gameResponseDetailList.toList()))
    }

}