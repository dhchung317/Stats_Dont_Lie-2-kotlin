package com.hyunki.statsdontlie2.view.fragments.game.utils

import com.hyunki.statsdontlie2.constants.GameConstants
import com.hyunki.statsdontlie2.model.NBAPlayer

class GameRoundData(private val pair: Pair<NBAPlayer, NBAPlayer>, val question: Question) {
    private lateinit var answer: NBAPlayer
    private var isCorrect: Boolean = false

    private val p1 = pair.first
    private val p2 = pair.second

    private var p1Stat = 0.0
    private var p2Stat = 0.0

    init {
        evaluateAnswer(question)
    }

    private fun evaluateAnswer(q: Question) {
        when (q.id) {
            "playerPointAvg" -> comparePlayerPointAvg()
            "playerAssistAvg" -> comparePlayerAssistAvg()
            "playerBlocksAvg" -> comparePlayerBlocksAvg()
            "playerDefRebAvg" -> comparePlayerDefRebAvg()
            "player3PM" -> comparePlayer3PM()
            "player3PA" -> comparePlayer3PA()
            else -> false
        }
    }

    private fun comparePlayer3PA() {
        answer = if (p1.player3PA > p2.player3PA) {
            p1
        } else {
            p2
        }

        p1Stat = p1.player3PA
        p2Stat = p2.player3PA
    }

    private fun comparePlayer3PM() {
        answer = if (p1.player3PM > p2.player3PM) {
            p1
        } else {
            p2
        }

        p1Stat = p1.player3PM
        p2Stat = p2.player3PM
    }

    private fun comparePlayerDefRebAvg() {
        answer = if (p1.playerDefRebAvg > p2.playerDefRebAvg) {
            p1
        } else {
            p2
        }

        p1Stat = p1.playerDefRebAvg
        p2Stat = p2.playerDefRebAvg
    }

    private fun comparePlayerBlocksAvg() {
        answer = if (p1.playerBlocksAvg > p2.playerBlocksAvg) {
            p1
        } else {
            p2
        }
        p1Stat = p1.playerBlocksAvg
        p2Stat = p2.playerBlocksAvg
    }

    private fun comparePlayerAssistAvg() {
        answer = if (p1.playerAssistAvg > p2.playerAssistAvg) {
            p1
        } else {
            p2
        }
        p1Stat = p1.playerAssistAvg
        p2Stat = p2.playerAssistAvg
    }

    private fun comparePlayerPointAvg() {
        answer = if (p1.playerPointAvg > p2.playerPointAvg) {
            p1
        } else {
            p2
        }
        p1Stat = p1.playerPointAvg
        p2Stat = p2.playerPointAvg
    }

    fun isCorrect(): Boolean {
        return isCorrect
    }

    fun setCorrect(isCorrect: Boolean) {
        this.isCorrect = isCorrect
    }

    fun getPlayer1(): NBAPlayer {
        return p1
    }

    fun getPlayer2(): NBAPlayer {
        return p2
    }

    fun getPlayer1Stat(): Double {
        return p1Stat
    }

    fun getPlayer2Stat(): Double {
        return p2Stat
    }

    fun getAnswer(): NBAPlayer {
        return answer
    }


}

//0 -> playerPointAvg
//1 -> playerAssistAvg
//2 -> playerBlocksAvg
//3 -> playerDefRebAvg
//4 -> player3PM
//5 -> player3PA
