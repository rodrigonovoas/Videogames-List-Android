package app.rodrigonovoa.myvideogameslist.ui.sharedFragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.network.ApiRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommonListViewModel(private val repository: ApiRepository): ViewModel() {
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

}