package com.hyunki.statsdontlie2.localdb

import android.util.Log
import com.hyunki.statsdontlie2.constants.BDLAppConstants
import com.hyunki.statsdontlie2.model.PlayerAverageModel
import java.util.*
import javax.inject.Inject

class BDLDatabaseRepositoryImpl @Inject constructor(private val bdlDatabase: BDLDatabase) : BDLDatabaseRepository {
    override fun addPlayerData(playerAverageModel: PlayerAverageModel) {
        bdlDatabase.addNBAPlayers(playerAverageModel)
    }

    override fun getPlayerAverageModelById(playerID: Int): PlayerAverageModel {
        val q = bdlDatabase!!.nBAPlayerQueries!!.selectById(playerID.toLong())
        Log.d("danny", q.executeAsList().toString() + playerID)
        return PlayerAverageModel(
                q.executeAsOne().playerID,
                q.executeAsOne().firstName,
                q.executeAsOne().lastName,
                q.executeAsOne().image,
                q.executeAsOne().playerPointAvg!!,
                q.executeAsOne().playerAssistAvg!!,
                q.executeAsOne().playerBlocksAvg!!,
                q.executeAsOne().playerDefRebAvg!!,
                q.executeAsOne().player3PM!!,
                q.executeAsOne().player3PA!!
        )
    }

    override val playerAverageModelList: List<PlayerAverageModel>
        get() {
            val playerAverageModelList: MutableList<PlayerAverageModel> = ArrayList()
            for (i in BDLAppConstants.PLAYER_ARRAY_CONSTANTS) {
                playerAverageModelList.add(getPlayerAverageModelById(i))
            }
            return playerAverageModelList
        }
}