package app.rodrigonovoa.myvideogameslist.view.ui.userOptions

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.myvideogameslist.persistance.sharedPreferences.Prefs
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserOptionsViewModel(private val repository: GamesListRepository, private val prefs: Prefs): ViewModel() {
    fun updateUsername(username: String, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
           val updated = repository.updateUsername(username,id)

            if(updated > 0){
                prefs.username = username
                Log.d("","updated")
            }
        }
    }
}