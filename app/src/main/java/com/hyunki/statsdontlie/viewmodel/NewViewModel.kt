package com.hyunki.statsdontlie.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.liveData
import com.hyunki.statsdontlie.constants.BDLAppConstants
import com.hyunki.statsdontlie.localdb.BDLDatabaseRepositoryImpl
import com.hyunki.statsdontlie.model.PlayerAverageModel
import com.hyunki.statsdontlie.network.ResponseState
import com.hyunki.statsdontlie.repository.BDLRepository
import com.hyunki.statsdontlie.utils.GameStatUtil
import com.hyunki.statsdontlie.utils.PlayerModelCreator
import com.hyunki.statsdontlie.utils.PlayerUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import java.util.*

class NewViewModel(private val databaseRepository: BDLDatabaseRepositoryImpl, private val repository: BDLRepository) : ViewModel() {

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

    companion object {
        fun getInstance(activity: AppCompatActivity?): NewViewModel {
            return ViewModelProviders.of(activity!!).get(NewViewModel::class.java)
        }
    }
}