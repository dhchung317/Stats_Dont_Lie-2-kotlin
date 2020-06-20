package com.hyunki.statsdontlie.repository

import android.annotation.SuppressLint
import com.hyunki.statsdontlie.model.BDLResponse
import com.hyunki.statsdontlie.network.BDLService

class BDLRepository(private val service: BDLService) : BaseRepository {
    @SuppressLint("CheckResult")
    suspend fun callBDLResponseClient(playerId: Int): BDLResponse {
        return service.getPlayerData(playerId, 2018, 100)
    }

}