package com.hyunki.statsdontlie2.localdb

import com.hyunki.statsdontlie2.model.PlayerAverageModel

interface BDLDatabaseRepository {
    fun addPlayerData(playerAverageModel: PlayerAverageModel)
    fun getPlayerAverageModelById(playerID: Int): PlayerAverageModel
    val playerAverageModelList: List<PlayerAverageModel>
}