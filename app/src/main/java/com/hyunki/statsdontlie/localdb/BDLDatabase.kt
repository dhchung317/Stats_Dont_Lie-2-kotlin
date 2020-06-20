package com.hyunki.statsdontlie.localdb

import android.content.Context
import com.hyunki.sql.NBAPlayerQueries
import com.hyunki.statsdontlie.Database
import com.hyunki.statsdontlie.Database.Companion.Schema
import com.hyunki.statsdontlie.model.PlayerAverageModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class BDLDatabase private constructor(context: Context) {

    private fun getDatabase(context: Context): Database? {
        val sqlDriver: SqlDriver = AndroidSqliteDriver(Schema, context, "BDL.db")
        if (database == null) {
            database = Database(sqlDriver)
        }
        return database
    }

    val nBAPlayerQueries: NBAPlayerQueries?
        get() {
            nbaPlayerQueries = database!!.nBAPlayerQueries
            return nbaPlayerQueries
        }

    fun addNBAPlayers(playerAverageModel: PlayerAverageModel) {
        database!!.nBAPlayerQueries.insertOrReplace(
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

    companion object {
        private var instance: BDLDatabase? = null
        private var database: Database? = null
        private var nbaPlayerQueries: NBAPlayerQueries? = null
        @JvmStatic
        fun getInstance(context: Context): BDLDatabase? {
            if (instance == null) {
                instance = BDLDatabase(context)
            }
            return instance
        }
    }

    init {
        database = getDatabase(context)
    }
}