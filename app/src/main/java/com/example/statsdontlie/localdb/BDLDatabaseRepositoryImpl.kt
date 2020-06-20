package com.example.statsdontlie.localdb

import android.app.Application
import android.util.Log
import com.example.statsdontlie.constants.BDLAppConstants
import com.example.statsdontlie.localdb.BDLDatabase.Companion.getInstance
import com.example.statsdontlie.model.PlayerAverageModel
import java.util.*

class BDLDatabaseRepositoryImpl private constructor(application: Application) : BDLDatabaseRepository {
    override fun addPlayerData(playerAverageModel: PlayerAverageModel?) {
        bdlDatabase!!.addNBAPlayers(playerAverageModel!!)
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

    companion object {
        private var bdlDatabase: BDLDatabase?
        private var instance: BDLDatabaseRepositoryImpl? = null
        fun getInstance(application: Application): BDLDatabaseRepositoryImpl? {
            if (instance == null) {
                instance = BDLDatabaseRepositoryImpl(application)
            }
            return instance
        }
    }

    init {
        bdlDatabase = getInstance(application.applicationContext)
    }
}