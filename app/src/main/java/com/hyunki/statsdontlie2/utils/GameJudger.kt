package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.model.PlayerAverageModel

class GameJudger(private val player1: PlayerAverageModel,
                 private val player2: PlayerAverageModel,
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
        return if (player1.playerPointAvg > player2.playerPointAvg &&
                playerChoice == 1) {
            true
        } else player2.playerPointAvg > player1.playerPointAvg &&
                playerChoice == 2
    }

    private fun playerAssistAvgComp(): Boolean {
        return if (player1.playerAssistAvg > player2.playerAssistAvg &&
                playerChoice == 1) {
            true
        } else player2.playerAssistAvg > player1.playerAssistAvg &&
                playerChoice == 2
    }

    private fun playerBlocksAvgComp(): Boolean {
        return if (player1.playerBlocksAvg > player2.playerBlocksAvg &&
                playerChoice == 1) {
            true
        } else player2.playerBlocksAvg > player1.playerBlocksAvg &&
                playerChoice == 2
    }

    private fun playerDefRebAvgComp(): Boolean {
        return if (player1.playerDefRebAvg > player2.playerDefRebAvg &&
                playerChoice == 1) {
            true
        } else player2.playerDefRebAvg > player1.playerDefRebAvg &&
                playerChoice == 2
    }

    private fun player3PM(): Boolean {
        return if (player1.player3PM > player2.player3PM &&
                playerChoice == 1) {
            true
        } else player2.player3PM > player1.player3PM &&
                playerChoice == 2
    }

    private fun player3PA(): Boolean {
        return if (player1.player3PA > player2.player3PA &&
                playerChoice == 1) {
            true
        } else player2.player3PA > player1.player3PA &&
                playerChoice == 2
    }

}