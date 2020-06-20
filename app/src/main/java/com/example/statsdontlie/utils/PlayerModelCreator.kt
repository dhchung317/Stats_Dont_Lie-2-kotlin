package com.example.statsdontlie.utils

import com.example.statsdontlie.model.PlayerAverageModel

object PlayerModelCreator {
    fun createPlayerModel(playerID: Long,
                          firstName: String,
                          lastName: String,
                          image: String,
                          gameStatUtil: GameStatUtil): PlayerAverageModel {
        return PlayerAverageModel(
                playerID,
                firstName,
                lastName,
                image,
                gameStatUtil.pointsAverage,
                gameStatUtil.playerAssistAvg,
                gameStatUtil.playerBlocksAvg,
                gameStatUtil.playerDefRebAvg,
                gameStatUtil.player3pMade,
                gameStatUtil.player3pAttempted)
    }
}