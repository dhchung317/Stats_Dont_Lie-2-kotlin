package com.hyunki.statsdontlie.utils

import com.hyunki.statsdontlie.model.BDLResponse.GameStats

class GameStatUtil(private val playerGameStatsList: List<GameStats?>?) {
    var pointsAverage = 0.0
        private set
    var playerAssistAvg = 0.0
        private set
    var playerBlocksAvg = 0.0
        private set
    var playerDefRebAvg = 0.0
        private set
    var player3pMade = 0.0
        private set
    var player3pAttempted = 0.0
        private set
    private val playerGameStatsListSize: Int
    private fun calculateAllStatsTotals() {
        for (gameStat in playerGameStatsList!!) {
            pointsAverage += gameStat.getPts().toDouble()
            playerAssistAvg += gameStat.getAst().toDouble()
            playerBlocksAvg += gameStat.getBlk().toDouble()
            playerDefRebAvg += gameStat.getDreb().toDouble()
            player3pMade += gameStat.getFg3m().toDouble()
            player3pAttempted += gameStat.getFg3a().toDouble()
        }
    }

    private fun calculateAllStatsAverages() {
        calculatePtsAvg()
        calculatePlayerAssistAvg()
        calculatePlayerBlkAvg()
        calculateDefRbnAvg()
        calculatePlayer3pMade()
        calculatePlayer3pAttempted()
    }

    private fun calculatePtsAvg() {
        pointsAverage /= playerGameStatsListSize.toDouble()
    }

    private fun calculatePlayerAssistAvg() {
        playerAssistAvg /= playerGameStatsListSize.toDouble()
    }

    private fun calculatePlayerBlkAvg() {
        playerBlocksAvg /= playerGameStatsListSize.toDouble()
    }

    private fun calculateDefRbnAvg() {
        playerDefRebAvg /= playerGameStatsListSize.toDouble()
    }

    private fun calculatePlayer3pMade() {
        player3pMade /= playerGameStatsListSize.toDouble()
    }

    private fun calculatePlayer3pAttempted() {
        player3pAttempted /= playerGameStatsListSize.toDouble()
    }

    init {
        playerGameStatsListSize = playerGameStatsList!!.size
        calculateAllStatsTotals()
        calculateAllStatsAverages()
    }
}