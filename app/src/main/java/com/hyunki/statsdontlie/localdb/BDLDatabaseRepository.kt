package com.hyunki.statsdontlie.localdb

import com.hyunki.statsdontlie.model.PlayerAverageModel

internal interface BDLDatabaseRepository {
    fun addPlayerData(playerAverageModel: PlayerAverageModel)
    fun getPlayerAverageModelById(playerID: Int): PlayerAverageModel
    val playerAverageModelList: List<PlayerAverageModel>
}