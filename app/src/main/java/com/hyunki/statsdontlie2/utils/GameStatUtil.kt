package com.hyunki.statsdontlie2.utils


import com.hyunki.statsdontlie2.model.GameStats

class GameStatUtil(private val playerGameStatsList: List<GameStats>) {
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
    private val playerGameStatsListSize: Int = playerGameStatsList.size
    private fun calculateAllStatsTotals() {
        for (gameStat in playerGameStatsList) {
            pointsAverage += gameStat.pts.toDouble()
            playerAssistAvg += gameStat.ast.toDouble()
            playerBlocksAvg += gameStat.blk.toDouble()
            playerDefRebAvg += gameStat.dreb.toDouble()
            player3pMade += gameStat.fg3m.toDouble()
            player3pAttempted += gameStat.fg3a.toDouble()
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
        calculateAllStatsTotals()
        calculateAllStatsAverages()
    }
}