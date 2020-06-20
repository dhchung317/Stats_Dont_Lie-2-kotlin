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

    override fun addPlayerImage(playerId: Int, image: ByteArray) {
        bdlDatabase.addPlayerImage(playerId, image)
    }

    override fun getPlayerImage(playerId: Int): Bitmap? {
        return BitmapFactory.decodeByteArray(
                bdlDatabase.getPlayerImage(playerId),
                0,
                bdlDatabase.getPlayerImage(playerId)!!.size
        )
    }




//    override val playerAverageModelList: List<PlayerAverageModel>
//        get() {
//            val playerAverageModelList: MutableList<PlayerAverageModel> = ArrayList()
//            for (i in BDLAppConstants.PLAYER_ARRAY_CONSTANTS) {
//                playerAverageModelList.add(getPlayerAverageModelById(i))
//            }
//            return playerAverageModelList
//        }
}