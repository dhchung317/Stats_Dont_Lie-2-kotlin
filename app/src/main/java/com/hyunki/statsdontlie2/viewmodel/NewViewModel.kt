package com.hyunki.statsdontlie2.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.liveData
import com.hyunki.statsdontlie2.constants.BDLAppConstants
import com.hyunki.statsdontlie2.localdb.BDLDatabaseRepository
import com.hyunki.statsdontlie2.localdb.BDLDatabaseRepositoryImpl
import com.hyunki.statsdontlie2.model.PlayerAverageModel
import com.hyunki.statsdontlie2.network.ResponseState
import com.hyunki.statsdontlie2.repository.Repository
import com.hyunki.statsdontlie2.repository.RepositoryImpl
import com.hyunki.statsdontlie2.utils.GameStatUtil
import com.hyunki.statsdontlie2.utils.PlayerModelCreator
import com.hyunki.statsdontlie2.utils.PlayerUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NewViewModel @Inject
constructor(private val databaseRepository: BDLDatabaseRepository, private val repository: Repository) : ViewModel() {

    fun callBDLResponseClient() = liveData(Dispatchers.IO) {
        emit(ResponseState.Loading)

        val playerIdLists: MutableList<Int> = ArrayList()
        for (playerIds in BDLAppConstants.PLAYER_ARRAY_CONSTANTS) {
            playerIdLists.add(playerIds)
        }
        try{
            withContext(Dispatchers.Default) {
                val playerAverageModels = async {
                    playerIdLists.asFlow().map { id -> repository.callBDLResponseClient(id) }
                            .map { res ->
                                val gameStatUtil = GameStatUtil(res.data)
                                val currentPlayer = res.data[0].player
                                return@map PlayerModelCreator.createPlayerModel(
                                        currentPlayer.id.toLong(),
                                        currentPlayer.firstName,
                                        currentPlayer.lastName,
                                        PlayerUtil.getPlayerPhotoUrl(currentPlayer.firstName, currentPlayer.lastName),
                                        gameStatUtil
                                )
                            }.toList()
                }
                emit(ResponseState.Success.OnResponsesLoaded(playerAverageModels.await()))
            }
        } catch(e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun getPlayerAverageModels(): List<PlayerAverageModel> {
        return databaseRepository.playerAverageModelList
    }
}