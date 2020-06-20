package com.hyunki.statsdontlie.viewmodel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.hyunki.statsdontlie.constants.BDLAppConstants
import com.hyunki.statsdontlie.localdb.BDLDatabaseRepositoryImpl
import com.hyunki.statsdontlie.model.PlayerAverageModel
import com.hyunki.statsdontlie.network.RetrofitSingleton
import com.hyunki.statsdontlie.repository.BDLRepository
import com.hyunki.statsdontlie.utils.GameStatUtil
import com.hyunki.statsdontlie.utils.PlayerModelCreator
import com.hyunki.statsdontlie.utils.PlayerUtil
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.util.*

class NewViewModel(private val databaseRepository: BDLDatabaseRepositoryImpl, private val repository: BDLRepository) : ViewModel() {

    private lateinit var playerAverageModels: List<PlayerAverageModel>

    @SuppressLint("CheckResult")
    suspend fun callBDLResponseClient() {
        val playerIdLists: MutableList<Int> = ArrayList()
        for (playerIds in BDLAppConstants.PLAYER_ARRAY_CONSTANTS) {
            playerIdLists.add(playerIds)
        }
        playerAverageModels = playerIdLists.asFlow().map { id -> repository.callBDLResponseClient(id) }
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

    fun getPlayerAverageModels(): List<PlayerAverageModel> {
        return databaseRepository.playerAverageModelList
    }

    companion object {
        fun getInstance(activity: AppCompatActivity?): NewViewModel {
            return ViewModelProviders.of(activity!!).get(NewViewModel::class.java)
        }
    }
}