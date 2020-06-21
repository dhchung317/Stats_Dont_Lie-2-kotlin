package com.hyunki.statsdontlie2.view.fragments.game.utils

import com.hyunki.statsdontlie2.constants.GameConstants
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.view.fragments.game.controller.GameCommandsListener
import com.hyunki.statsdontlie2.model.NBAPlayer

class GameManager(gameRoster: List<NBAPlayer>, private val gameCommandsListener: GameCommandsListener, private val listener: OnFragmentInteractionListener) {
    private val playersLeft = gameRoster.toMutableList()

    private val gameBundle = GameRoundBundle()
    private lateinit var gameData: GameRoundData

    init {
        runClock()
        setUpGameManager()
    }

    private fun shuffleList() {
        playersLeft.shuffle()
    }

    private fun getRandomTwo(): Pair<NBAPlayer, NBAPlayer> {
        val playerOne = playersLeft.random()
        playersLeft.remove(playerOne)
        val playerTwo = playersLeft.random()
        playersLeft.remove(playerTwo)

        shuffleList()

        return Pair(playerOne, playerTwo)
    }

    private fun setUpGameManager() {
        gameData = GameRoundData(getRandomTwo(), GameConstants.QUESTIONS_ARRAY.random())
    }


    fun finishRound() {
        gameBundle.addGameData(gameData)
        if (playersLeft.size > 2) {
            setUpGameManager()
            runGame()
        } else {
            finishGame()
        }
    }

    private fun finishGame() {
        listener.displayResultFragment()
    }


    fun runGame() {
        gameCommandsListener.runGame()
    }

    fun runClock() {
        gameCommandsListener.runClock()
    }

    fun getRoundData(): GameRoundData {
        return gameData
    }

    fun checkAnswer(isRightPlayer: Boolean) {
        if (isRightPlayer) {
            gameData.setCorrect(true)
        } else {
            gameData.setCorrect(false)
        }
    }
}