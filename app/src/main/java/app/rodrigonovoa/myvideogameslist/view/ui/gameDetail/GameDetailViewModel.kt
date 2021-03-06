package app.rodrigonovoa.myvideogameslist.view.ui.gameDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.*
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(private val repository: GamesListRepository): ViewModel() {
    private val _retrievedGame = MutableLiveData<GameDetailResponse?>().apply { postValue(null)}
    val retrievedGame: LiveData<GameDetailResponse?> get() = _retrievedGame

    private val _disableAddButton = MutableLiveData<Boolean?>().apply { postValue(false)}
    val disableAddButton: LiveData<Boolean?> get() = _disableAddButton

    private val _disableAddPendingButton = MutableLiveData<Boolean?>().apply { postValue(false)}
    val disableAddPendingButton: LiveData<Boolean?> get() = _disableAddPendingButton

    fun setRetrievedGame(game: GameDetailResponse){
        _retrievedGame.postValue(game)
    }

    fun getGameFromRepo(id: Int){
        viewModelScope.launch {
            repository.getGameById(id)
                .catch {
                    Log.d("gamedetail", it.message.toString())
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

    fun checkIfGameRecordExists(gameId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val list =  repository.getGameRecordsByGameId(gameId)

            if(list.isNotEmpty()){
                _disableAddButton.postValue(true)
            }
        }
    }

    fun checkIfPendingGameExists(gameId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val game =  repository.getPendingGamesByGameId(gameId)

            if(game.isNotEmpty()){
                _disableAddPendingButton.postValue(true)
            }
        }
    }

    fun getGameFromLocalDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
           val game =  repository.getGameByIdFromDb(id)

            if(game != null){
                setRetrievedGame(map(game))
            }
        }
    }

    private fun map(game: Game):GameDetailResponse{
        return GameDetailResponse(0, game.name, game.description, game.metacritic,
            "21-04-2022", game.image, game.website, getPublishersFromGame(game.publisher) , getEsrbRatingFromGame(game.esrbRating), getPlatformsFromGame(game.platforms))
    }

    private fun getPublishersFromGame(content: String?): List<PublisherDetailResponse>{
        val list = mutableListOf<PublisherDetailResponse>()
        if(content != null){
            list.add(PublisherDetailResponse(0, content))
        }

        return list.toList()
    }

    private fun getPlatformsFromGame(content: String?): List<PlatformResponse>{
        val list = mutableListOf<PlatformResponse>()

        if(content != null){
            list.add(PlatformResponse(PlatformDetailResponse(0,content)))
        }

        return list.toList()
    }

    private fun getEsrbRatingFromGame(content: String?): EsrbRatingDetailResponse?{
        if(content != null){
            return EsrbRatingDetailResponse(content)
        }else{
            return null
        }
    }

}