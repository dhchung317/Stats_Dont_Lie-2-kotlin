package com.hyunki.statsdontlie2.repository

import com.hyunki.statsdontlie2.model.BDLResponse

interface Repository{
    suspend fun callBDLResponseClient(playerId: Int): BDLResponse
}