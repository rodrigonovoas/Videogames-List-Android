package app.rodrigonovoa.myvideogameslist.network

import app.rodrigonovoa.myvideogameslist.model.domain.GameResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("games?")
    fun getGames(): Call<GameResponse>
}