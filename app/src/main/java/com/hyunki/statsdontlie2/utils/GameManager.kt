package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.controller.GameCommandsListener
import com.hyunki.statsdontlie2.model.NBAPlayer
import java.util.*

class GameManager(gameRoster: List<NBAPlayer>, val gameCommandsListener: GameCommandsListener) {
    private val playersLeft = gameRoster.toMutableList()
    private lateinit var pair: Pair<NBAPlayer,NBAPlayer>

    private fun shuffleList() {
        playersLeft.shuffle()
    }

    private fun getRandomTwo(): Pair<NBAPlayer,NBAPlayer> {
        val playerOne = playersLeft.random()
        playersLeft.remove(playerOne)
        val playerTwo = playersLeft.random()
        playersLeft.remove(playerTwo)

        shuffleList()

        return Pair(playerOne, playerTwo)
    }


    fun runGame(){
        gameCommandsListener.runGame()
    }
}