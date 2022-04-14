package app.rodrigonovoa.myvideogameslist.network

import app.rodrigonovoa.myvideogameslist.Constants.Companion.KEY_ENDPOINT
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("games?" + KEY_ENDPOINT+ "&page=1&page_size=10")
    fun getGames(): Call<GamesListResponse>
}