package app.rodrigonovoa.myvideogameslist.view.ui.userOptions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserOptionsViewModel(private val repository: GamesListRepository): ViewModel() {
    fun updateUsername(username: String, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
           val updated = repository.updateUsername(username,id)

            if(updated > 0){
                Log.d("","updated")
            }
        }
    }
}