package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.model.NBAPlayer

class GameJudger(private val NBAPlayer1: NBAPlayer,
                 private val NBAPlayer2: NBAPlayer,
                 private val playerChoice: Int,
                 private val questionPosition: Int) {
    val isPlayerChoiceCorrect: Boolean
        get() = when (questionPosition) {
            0 -> playerPointAvgComp()
            1 -> playerAssistAvgComp()
            2 -> playerBlocksAvgComp()
            3 -> playerDefRebAvgComp()
            4 -> player3PM()
            5 -> player3PA()
            else -> false
        }

    private fun playerPointAvgComp(): Boolean {
        
        
        
        return if (NBAPlayer1.playerPointAvg > NBAPlayer2.playerPointAvg &&
                playerChoice == 1) {
            true
        } else NBAPlayer2.playerPointAvg > NBAPlayer1.playerPointAvg &&
                playerChoice == 2
    }

    private fun playerAssistAvgComp(): Boolean {
        return if (NBAPlayer1.playerAssistAvg > NBAPlayer2.playerAssistAvg &&
                playerChoice == 1) {
            true
        } else NBAPlayer2.playerAssistAvg > NBAPlayer1.playerAssistAvg &&
                playerChoice == 2
    }

    private fun playerBlocksAvgComp(): Boolean {
        return if (NBAPlayer1.playerBlocksAvg > NBAPlayer2.playerBlocksAvg &&
                playerChoice == 1) {
            true
        } else NBAPlayer2.playerBlocksAvg > NBAPlayer1.playerBlocksAvg &&
                playerChoice == 2
    }

    private fun playerDefRebAvgComp(): Boolean {
        return if (NBAPlayer1.playerDefRebAvg > NBAPlayer2.playerDefRebAvg &&
                playerChoice == 1) {
            true
        } else NBAPlayer2.playerDefRebAvg > NBAPlayer1.playerDefRebAvg &&
                playerChoice == 2
    }

    private fun player3PM(): Boolean {
        return if (NBAPlayer1.player3PM > NBAPlayer2.player3PM &&
                playerChoice == 1) {
            true
        } else NBAPlayer2.player3PM > NBAPlayer1.player3PM &&
                playerChoice == 2
    }

    private fun player3PA(): Boolean {
        return if (NBAPlayer1.player3PA > NBAPlayer2.player3PA &&
                playerChoice == 1) {
            true
        } else NBAPlayer2.player3PA > NBAPlayer1.player3PA &&
                playerChoice == 2
    }

}