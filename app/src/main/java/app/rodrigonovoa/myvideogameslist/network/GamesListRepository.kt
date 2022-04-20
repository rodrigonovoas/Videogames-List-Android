package app.rodrigonovoa.myvideogameslist.network

import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.model.localdb.User
import app.rodrigonovoa.myvideogameslist.room.GamesListDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class GamesListRepository(private val apiService: ApiService, private val localDb: GamesListDb) {
    // NETWORK
    suspend fun getGamesListFromRepository(): Flow<Response<GamesListResponse?>> = flow {
        val gameList = apiService.getGames().execute()

        emit(gameList)
    }.flowOn(Dispatchers.IO)

    suspend fun getGameById(id: Int): Flow<Response<GameDetailResponse?>> = flow {
        val game = apiService.getGameById(id).execute()

        emit(game)
    }.flowOn(Dispatchers.IO)

    // ROOM
    suspend fun insertUserIntoDb(user: User){
        localDb.userDao().insert(user)
    }

    suspend fun getAllGamesFromDb(): List<Game>{
        return localDb.gameDao().getAll()
    }

    suspend fun getGameByIdFromDb(id: Int): Game {
        return localDb.gameDao().getGameById(id)
    }

    suspend fun insertGame(game: Game): Long{
        return localDb.gameDao().insert(game)
    }

    suspend fun insertGameRecord(record: GameRecord): Long{
        return localDb.gameRecordDao().insert(record)
    }

}