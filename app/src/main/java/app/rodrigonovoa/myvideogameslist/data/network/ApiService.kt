package app.rodrigonovoa.myvideogameslist.data.network

import app.rodrigonovoa.myvideogameslist.Constants.Companion.KEY_ENDPOINT
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.GamesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("games?" + KEY_ENDPOINT+ "&page=1&page_size=10")
    fun getGames(): Call<GamesListResponse>

    @GET("games/{id}?" + KEY_ENDPOINT+ "")
    fun getGameById(@Path("id") id: Int): Call<GameDetailResponse>
}