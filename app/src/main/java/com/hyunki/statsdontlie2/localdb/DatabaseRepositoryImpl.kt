package com.hyunki.statsdontlie2.localdb


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.hyunki.statsdontlie2.model.NBAPlayer
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val database: Database) : DatabaseRepository {

    override fun addAllPlayerData(NBAPlayers: List<NBAPlayer>) {
        database.addAllPlayerData(NBAPlayers)
    }

    override fun getAllPlayerData(): List<NBAPlayer> {
        return database.getAllPlayerData()
    }

    override fun getPlayerAverageModelById(playerID: Int): NBAPlayer {
        return database.getPlayerById(playerID)
    }

    override fun addPlayerImage(playerID: Int, image: ByteArray) {
        database.addPlayerImage(playerID, image)
    }

    override fun getPlayerImage(playerID: Int): Bitmap? {
        return BitmapFactory.decodeByteArray(
                database.getPlayerImage(playerID),
                0,
                database.getPlayerImage(playerID)!!.size
        )
    }
}