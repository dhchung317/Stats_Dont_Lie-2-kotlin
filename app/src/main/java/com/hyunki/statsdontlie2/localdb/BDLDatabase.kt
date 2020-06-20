package com.hyunki.statsdontlie2.localdb

import com.hyunki.sql.NBAPlayerQueries
import com.hyunki.statsdontlie2.Database
import com.hyunki.statsdontlie2.model.PlayerAverageModel
import javax.inject.Inject

class BDLDatabase @Inject constructor(private val database: Database) {

    val nBAPlayerQueries: NBAPlayerQueries
        get() {
            return database.nBAPlayerQueries
        }

    fun addNBAPlayers(playerAverageModel: PlayerAverageModel) {
        database.nBAPlayerQueries.insertOrReplace(
                playerAverageModel.playerID,
                playerAverageModel.firstName,
                playerAverageModel.lastName,
                playerAverageModel.image,
                playerAverageModel.playerPointAvg,
                playerAverageModel.playerAssistAvg,
                playerAverageModel.playerBlocksAvg,
                playerAverageModel.playerDefRebAvg,
                playerAverageModel.player3PM,
                playerAverageModel.player3PA
        )
    }

}