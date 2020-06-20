package com.hyunki.statsdontlie2.localdb


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




//    override val playerAverageModelList: List<PlayerAverageModel>
//        get() {
//            val playerAverageModelList: MutableList<PlayerAverageModel> = ArrayList()
//            for (i in BDLAppConstants.PLAYER_ARRAY_CONSTANTS) {
//                playerAverageModelList.add(getPlayerAverageModelById(i))
//            }
//            return playerAverageModelList
//        }
}