package app.rodrigonovoa.myvideogameslist.network

import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {
    suspend fun getGamesListFromRepository(): Flow<Response<GamesListResponse?>> = flow {
        val gameList = apiService.getGames().execute()

        emit(gameList)
    }.flowOn(Dispatchers.IO)

    suspend fun getGameById(id: Int): Flow<Response<GameDetailResponse?>> = flow {
        val game = apiService.getGameById(id).execute()

        emit(game)
    }.flowOn(Dispatchers.IO)
}