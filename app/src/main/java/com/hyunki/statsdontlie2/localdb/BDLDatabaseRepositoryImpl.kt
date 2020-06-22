package com.hyunki.statsdontlie2.localdb


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.hyunki.statsdontlie2.model.NBAPlayer
import javax.inject.Inject

class BDLDatabaseRepositoryImpl @Inject constructor(private val bdlDatabase: BDLDatabase) : BDLDatabaseRepository {

    override fun addAllPlayerData(NBAPlayers: List<NBAPlayer>) {
        bdlDatabase.addAllPlayerData(NBAPlayers)
    }

    override fun getAllPlayerData(): List<NBAPlayer> {
        return bdlDatabase.getAllPlayerData()
    }

    override fun getPlayerAverageModelById(playerID: Int): NBAPlayer {
        return bdlDatabase.getPlayerById(playerID)
    }

    override fun addPlayerImage(playerID: Int, image: ByteArray) {
        bdlDatabase.addPlayerImage(playerID, image)
    }

    override fun getPlayerImage(playerID: Int): Bitmap? {
        return BitmapFactory.decodeByteArray(
                bdlDatabase.getPlayerImage(playerID),
                0,
                bdlDatabase.getPlayerImage(playerID)!!.size
        )
    }
}