package com.hyunki.statsdontlie2.localdb

import com.hyunki.statsdontlie2.Database
import com.hyunki.statsdontlie2.model.NBAPlayer

import javax.inject.Inject

class BDLDatabase @Inject constructor(private val database: Database) {

    fun addAllPlayerData(NBAPlayers: List<NBAPlayer>) {
        database.savedNBAPlayerQueries.transaction {
            NBAPlayers.forEach { player ->
                database.savedNBAPlayerQueries.insertOrReplace(
                        playerID = player.playerID,
                        firstName = player.firstName,
                        lastName = player.lastName,
                        image = player.image,
                        playerPointAvg = player.playerPointAvg,
                        playerAssistAvg = player.playerAssistAvg,
                        playerBlocksAvg = player.playerBlocksAvg,
                        playerDefRebAvg = player.playerDefRebAvg,
                        player3PA = player.player3PA,
                        player3PM = player.player3PM
                )
            }
        }
    }

    fun getAllPlayerData(): List<NBAPlayer> {
        val savedList = database.savedNBAPlayerQueries.selectAll().executeAsList()
        val convertedList = mutableListOf<NBAPlayer>()
        savedList.forEach { player ->
            convertedList.add(NBAPlayer(
                    playerID = player.playerID,
                    firstName = player.firstName,
                    lastName = player.lastName,
                    image = player.image,
                    playerPointAvg = player.playerPointAvg ?: 0.0,
                    playerAssistAvg = player.playerAssistAvg ?: 0.0,
                    playerBlocksAvg = player.playerBlocksAvg ?: 0.0,
                    playerDefRebAvg = player.playerDefRebAvg ?: 0.0,
                    player3PA = player.player3PA ?: 0.0,
                    player3PM = player.player3PM ?: 0.0)
            )
        }
        return convertedList
    }

    fun getPlayerById(playerID: Int): NBAPlayer {
        val player = database.savedNBAPlayerQueries.selectById(playerID.toLong()).executeAsOne()
        return player as NBAPlayer
//        return NBAPlayer(
//                player.executeAsOne().playerID,
//                player.executeAsOne().firstName,
//                player.executeAsOne().lastName,
//                player.executeAsOne().image,
//                player.executeAsOne().playerPointAvg!!,
//                player.executeAsOne().playerAssistAvg!!,
//                player.executeAsOne().playerBlocksAvg!!,
//                player.executeAsOne().playerDefRebAvg!!,
//                player.executeAsOne().player3PM!!,
//                player.executeAsOne().player3PA!!
//        )
    }


}