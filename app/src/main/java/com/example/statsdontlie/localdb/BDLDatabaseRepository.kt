package com.example.statsdontlie.localdb

import com.example.statsdontlie.model.PlayerAverageModel

internal interface BDLDatabaseRepository {
    fun addPlayerData(playerAverageModel: PlayerAverageModel?)
    fun getPlayerAverageModelById(playerID: Int): PlayerAverageModel
    val playerAverageModelList: List<PlayerAverageModel>
}