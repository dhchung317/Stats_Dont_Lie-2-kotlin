package com.hyunki.statsdontlie2.repository

import android.annotation.SuppressLint
import com.hyunki.statsdontlie2.model.BDLResponse
import com.hyunki.statsdontlie2.network.BDLService
import javax.inject.Inject

class RepositoryImpl
@Inject constructor(private val service: BDLService) : Repository {
    @SuppressLint("CheckResult")
    override suspend fun callBDLResponseClient(playerId: Int): BDLResponse = service.getPlayerData(playerId, 2018, 100)


}