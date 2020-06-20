package com.example.statsdontlie.repository

import android.annotation.SuppressLint
import com.example.statsdontlie.model.BDLResponse
import com.example.statsdontlie.network.BDLService
import io.reactivex.Single

class BDLRepository(private val service: BDLService) : BaseRepository {
    @SuppressLint("CheckResult")
    fun callBDLResponseClient(playerId: Int): Single<BDLResponse?>? {
        return service.getPlayerData(playerId, 2018, 100)
    }

}