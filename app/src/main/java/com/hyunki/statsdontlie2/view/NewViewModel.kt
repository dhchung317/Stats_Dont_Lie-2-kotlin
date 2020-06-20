package com.hyunki.statsdontlie2.view

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hyunki.statsdontlie2.constants.GameConstants
import com.hyunki.statsdontlie2.localdb.BDLDatabaseRepository
import com.hyunki.statsdontlie2.model.NBAPlayer
import com.hyunki.statsdontlie2.network.ResponseState
import com.hyunki.statsdontlie2.repository.Repository
import com.hyunki.statsdontlie2.utils.GameStatUtil
import com.hyunki.statsdontlie2.utils.ImageUtil
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

    private var incorrectGuesses: Int = 0
    private var correctGuesses: Int = 0

    fun callBDLResponseClient() = liveData(Dispatchers.IO) {
        emit(ResponseState.Loading)

        val playerIdLists: MutableList<Int> = ArrayList()

        for (playerIds in GameConstants.PLAYER_ARRAY_CONSTANTS) {
            playerIdLists.add(playerIds)
        }

        try {
            withContext(Dispatchers.Default) {
                val res = async {
                    playerIdLists.asFlow().map { id ->
                        repository.callBDLResponseClient(id)
                    }.toList()
                }

                val playerAverageModels = async {
                    res.await().asFlow().map { res ->
                        val gameStatUtil = GameStatUtil(res.data)

                        val currentPlayer = res.data[0].player
                        Log.d(TAG, "callBDLResponseClient: " + res.data.get(0).pts)
                        val imgUrl = PlayerUtil.getPlayerPhotoUrl(currentPlayer.first_name, currentPlayer.last_name)

                        withContext(Dispatchers.Default) {
                            saveImageToDatabase(
                                    currentPlayer.id,
                                    ImageUtil.getBitmapAsByteArray(ImageUtil.getBitmapFromURL(imgUrl)))
                        }

                        return@map PlayerModelCreator.createPlayerModel(
                                currentPlayer.id.toLong(),
                                currentPlayer.first_name,
                                currentPlayer.last_name,
                                imgUrl,
                                gameStatUtil
                        )
                    }.toList()
                }
                emit(ResponseState.Success.OnResponsesLoaded(playerAverageModels.await()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun getPlayerAverageModels(): List<NBAPlayer> {
        return databaseRepository.getAllPlayerData()
    }

    fun saveAllPlayers(nbaPlayers: List<NBAPlayer>) {
        databaseRepository.addAllPlayerData(nbaPlayers)
    }

    private fun saveImageToDatabase(playerID: Int, image: ByteArray) {
        databaseRepository.addPlayerImage(playerID, image)
    }

    fun getImageFromDatabase(playerID: Int): Bitmap? {
        return databaseRepository.getPlayerImage(playerID)
    }

    fun setCorrectGuesses(correct: Int) {
        correctGuesses = correct
    }

    fun setIncorrectGuesses(incorrect: Int) {
        incorrectGuesses = incorrect
    }

    fun getCorrectGuesses(): Int{
        return correctGuesses
    }

    fun getIncorrectGuesses(): Int {
        return incorrectGuesses
    }

    companion object {
        const val TAG = "main-view-model"
    }
}