package app.rodrigonovoa.myvideogameslist.repository

import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.*
import app.rodrigonovoa.myvideogameslist.network.ApiService
import app.rodrigonovoa.myvideogameslist.persistance.room.GamesListDb
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

    /**
     * USER
     */
    suspend fun insertUserIntoDb(user: User){
        localDb.userDao().insert(user)
    }

    suspend fun getUser(): User{
        return localDb.userDao().getFirstUSer()
    }

    /**
     * GAMES
     */
    suspend fun getAllGamesFromDb(): List<Game>{
        return localDb.gameDao().getAll()
    }

    suspend fun getAllGamesInGameRecorFromDb(): List<Game>{
        return localDb.gameDao().getAllFromGameRecord()
    }

    suspend fun getGameByIdFromDb(id: Int): Game {
        return localDb.gameDao().getGameById(id)
    }

    suspend fun insertGame(game: Game): Long{
        return localDb.gameDao().insert(game)
    }

    suspend fun deleteGame(game: Game): Int{
        return localDb.gameDao().delete(game)
    }

    suspend fun deleteGameById(id: Int): Int{
        return localDb.gameDao().deleteById(id)
    }

    /**
     * RECORDS
     */

    suspend fun getGameRecordsById(id:Int): GameRecord{
        return localDb.gameRecordDao().getRecordById(id)
    }

    suspend fun getGameRecordsByGameId(id:Int): List<GameRecord>{
        return localDb.gameRecordDao().getRecordByGameId(id)
    }

    suspend fun getFinishDateByGameId(id:Int): Long {
        return localDb.gameRecordDao().getFinishDateByGameId(id)
    }

    suspend fun insertGameRecord(record: GameRecord): Long{
        return localDb.gameRecordDao().insert(record)
    }

    suspend fun deleteGameRecord(record: GameRecord): Int {
        return localDb.gameRecordDao().delete(record)
    }

    /**
     * PENDING GAMES
     */

    suspend fun getAllPendingGame(): List<PendingGame>{
        return localDb.pendingGameDao().getAll()
    }

    suspend fun getAllPendingGameWithDetail(): List<PendingGameDetail>{
        return localDb.pendingGameDao().getAllWithDetail()
    }

   suspend fun getPendingGameById(id:Int): PendingGame{
        return localDb.pendingGameDao().getPendingById(id)
    }

    suspend fun getPendingGamesByGameId(id:Int): List<PendingGame>{
        return localDb.pendingGameDao().getPendingByGameId(id)
    }

    suspend fun insertPendingGame(pending: PendingGame): Long{
        return localDb.pendingGameDao().insert(pending)
    }

    suspend fun deletePendingGame(pending: PendingGame): Int {
        return localDb.pendingGameDao().delete(pending)
    }

}