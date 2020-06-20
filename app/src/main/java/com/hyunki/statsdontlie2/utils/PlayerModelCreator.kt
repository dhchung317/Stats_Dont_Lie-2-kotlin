package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.model.NBAPlayer

object PlayerModelCreator {
    fun createPlayerModel(playerID: Long,
                          firstName: String,
                          lastName: String,
                          image: String,
                          gameStatUtil: GameStatUtil): NBAPlayer {
        return NBAPlayer(
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