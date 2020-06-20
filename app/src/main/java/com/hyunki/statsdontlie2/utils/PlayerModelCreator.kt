package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.model.PlayerAverageModel

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