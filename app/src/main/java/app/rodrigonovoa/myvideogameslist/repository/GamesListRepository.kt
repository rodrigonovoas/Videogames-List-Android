package app.rodrigonovoa.myvideogameslist.repository

import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.data.model.localdb.User
import app.rodrigonovoa.myvideogameslist.data.network.ApiService
import app.rodrigonovoa.myvideogameslist.data.room.GamesListDb
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

    suspend fun getGameRecordsById(id:Int): GameRecord{
        return localDb.gameRecordDao().getRecordById(id)
    }

    suspend fun getGameRecordsByGameId(id:Int): List<GameRecord>{
        return localDb.gameRecordDao().getRecordByGameId(id)
    }

    suspend fun insertGameRecord(record: GameRecord): Long{
        return localDb.gameRecordDao().insert(record)
    }

}