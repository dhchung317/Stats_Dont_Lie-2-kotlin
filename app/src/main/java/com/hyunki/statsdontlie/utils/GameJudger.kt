package com.hyunki.statsdontlie.utils

import com.hyunki.statsdontlie.model.PlayerAverageModel

class GameJudger(private val player1: PlayerAverageModel?,
                 private val player2: PlayerAverageModel?,
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
        return if (player1.getPlayerPointAvg() > player2.getPlayerPointAvg() &&
                playerChoice == 1) {
            true
        } else player2.getPlayerPointAvg() > player1.getPlayerPointAvg() &&
                playerChoice == 2
    }

    private fun playerAssistAvgComp(): Boolean {
        return if (player1.getPlayerAssistAvg() > player2.getPlayerAssistAvg() &&
                playerChoice == 1) {
            true
        } else player2.getPlayerAssistAvg() > player1.getPlayerAssistAvg() &&
                playerChoice == 2
    }

    private fun playerBlocksAvgComp(): Boolean {
        return if (player1.getPlayerBlocksAvg() > player2.getPlayerBlocksAvg() &&
                playerChoice == 1) {
            true
        } else player2.getPlayerBlocksAvg() > player1.getPlayerBlocksAvg() &&
                playerChoice == 2
    }

    private fun playerDefRebAvgComp(): Boolean {
        return if (player1.getPlayerDefRebAvg() > player2.getPlayerDefRebAvg() &&
                playerChoice == 1) {
            true
        } else player2.getPlayerDefRebAvg() > player1.getPlayerDefRebAvg() &&
                playerChoice == 2
    }

    private fun player3PM(): Boolean {
        return if (player1.getPlayer3PM() > player2.getPlayer3PM() &&
                playerChoice == 1) {
            true
        } else player2.getPlayer3PM() > player1.getPlayer3PM() &&
                playerChoice == 2
    }

    private fun player3PA(): Boolean {
        return if (player1.getPlayer3PA() > player2.getPlayer3PA() &&
                playerChoice == 1) {
            true
        } else player2.getPlayer3PA() > player1.getPlayer3PA() &&
                playerChoice == 2
    }

}