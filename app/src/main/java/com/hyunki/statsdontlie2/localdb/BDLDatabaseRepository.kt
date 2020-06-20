package com.hyunki.statsdontlie2.localdb

import android.graphics.Bitmap
import com.hyunki.statsdontlie2.model.NBAPlayer


interface BDLDatabaseRepository {
    fun addAllPlayerData(NBAPlayers: List<NBAPlayer>)
    fun getPlayerAverageModelById(playerID: Int): NBAPlayer
    fun getAllPlayerData():List<NBAPlayer>
    fun addPlayerImage(playerID: Int, image: ByteArray)
    fun getPlayerImage(playerID: Int): Bitmap?
}