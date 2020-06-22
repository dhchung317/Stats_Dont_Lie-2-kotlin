package com.hyunki.statsdontlie2.view.fragments.game.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.hyunki.statsdontlie2.constants.GameConstants
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.view.fragments.game.controller.GameCommandsListener
import com.hyunki.statsdontlie2.model.NBAPlayer

class GameManager(gameRoster: List<NBAPlayer>, private val gameCommandsListener: GameCommandsListener, private val listener: OnFragmentInteractionListener) {
    private val playersList = gameRoster.toMutableList()

    private val gameBundle = GameRoundBundle()
    private lateinit var gameData: GameRoundData

    init {
        runClock()
        setUpGameManager()
    }

    private fun shuffleList() {
        playersList.shuffle()
    }

    private fun getRandomTwo(): Pair<NBAPlayer, NBAPlayer> {
        val playerOne = playersList.random()
        var playerTwo = playersList.random()
        while(playerOne == playerTwo){
            playerTwo = playersList.random()
        }
        shuffleList()
        return Pair(playerOne, playerTwo)
    }

    private fun setUpGameManager() {
        gameData = GameRoundData(getRandomTwo(), GameConstants.QUESTIONS_ARRAY.random())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun finishRound() {
        gameBundle.addGameData(gameData)
        setUpGameManager()
        runGame()
    }

    private fun runGame() {
        gameCommandsListener.runGame()
    }

    private fun runClock() {
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

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateResultsFromGameBundle(gameBundle: GameRoundBundle): Result {
        val dataList = gameBundle.getRoundDataList()
        val correct = dataList.stream()
                .map { it.isCorrect() }.filter { it == true }.count().toInt()
        val incorrect = dataList.size - correct
        return Result(incorrect = incorrect, correct = correct)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setResults() {
        val results = calculateResultsFromGameBundle(gameBundle)
        listener.setResultsDataFromGameManager(
                playerCorrectGuesses = results.correct, playerIncorrectGuesses = results.incorrect)
    }

    data class Result(val incorrect: Int, val correct: Int)
}