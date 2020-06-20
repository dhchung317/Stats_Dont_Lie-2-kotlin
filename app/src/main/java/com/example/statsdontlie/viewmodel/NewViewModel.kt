package com.example.statsdontlie.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.statsdontlie.constants.BDLAppConstants
import com.example.statsdontlie.localdb.BDLDatabaseRepositoryImpl
import com.example.statsdontlie.model.BDLResponse
import com.example.statsdontlie.model.PlayerAverageModel
import com.example.statsdontlie.network.RetrofitSingleton
import com.example.statsdontlie.repository.BDLRepository
import com.example.statsdontlie.utils.GameStatUtil
import com.example.statsdontlie.utils.PlayerModelCreator
import com.example.statsdontlie.utils.PlayerUtil
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class NewViewModel(application: Application) : AndroidViewModel(application) {
    val databaseRepository: BDLDatabaseRepositoryImpl?
    private val repository: BDLRepository
    private val playerAverageModels: MutableList<PlayerAverageModel?> = ArrayList()

    @SuppressLint("CheckResult")
    fun callBDLResponseClient(): Observable<PlayerAverageModel?> {
        val playerIdLists: MutableList<Int> = ArrayList()
        for (playerIds in BDLAppConstants.PLAYER_ARRAY_CONSTANTS) {
            playerIdLists.add(playerIds)
        }
        return Observable.fromIterable(playerIdLists)
                .map { playerId: Int -> repository.callBDLResponseClient(playerId) }
                .map { response: Single<BDLResponse?>? ->
                    val gameStatUtil = GameStatUtil(response!!.blockingGet().getData())
                    val currentPlayer = response!!.blockingGet().getData()[0].player
                    val playerAverageModel = PlayerModelCreator.createPlayerModel(
                            currentPlayer.id.toLong(),
                            currentPlayer.firstName,
                            currentPlayer.lastName,
                            PlayerUtil.getPlayerPhotoUrl(currentPlayer.firstName, currentPlayer.lastName),
                            gameStatUtil)
                    playerAverageModels.add(playerAverageModel)
                    playerAverageModel
                }
    }

    fun getPlayerAverageModels(): List<PlayerAverageModel?>? {
        return databaseRepository.getPlayerAverageModelList()
    }

    companion object {
        fun getInstance(activity: AppCompatActivity?): NewViewModel {
            return ViewModelProviders.of(activity!!).get(NewViewModel::class.java)
        }
    }

    init {
        repository = BDLRepository(RetrofitSingleton.getSingleService())
        databaseRepository = BDLDatabaseRepositoryImpl.Companion.getInstance(application)
    }
}