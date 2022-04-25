package app.rodrigonovoa.myvideogameslist.view.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.model.localdb.User
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: GamesListRepository): ViewModel() {
    private val _appUser = MutableLiveData<User?>().apply { postValue(null)}
    val appUser: LiveData<User?> get() = _appUser

    fun insertUser(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUserIntoDb(User(null, "Rodrigod"))
        }
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
           _appUser.postValue(repository.getUser())
        }
    }
}