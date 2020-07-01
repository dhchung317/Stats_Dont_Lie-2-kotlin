package com.hyunki.statsdontlie2.view.fragments.game.utils

class GameRoundBundle {
    private val rounds = mutableListOf<GameRoundData>()

    fun addGameData(data: GameRoundData) {
        rounds.add(data)
    }
    fun getRoundDataList(): List<GameRoundData>{
        return rounds
    }

    fun clearRounds(){
        rounds.clear()
    }
}