package com.hyunki.statsdontlie2.localdb

import com.hyunki.statsdontlie2.model.NBAPlayer


interface BDLDatabaseRepository {
    fun addAllPlayerData(NBAPlayers: List<NBAPlayer>)
    fun getPlayerAverageModelById(playerID: Int): NBAPlayer
    fun getAllPlayerData():List<NBAPlayer>
}