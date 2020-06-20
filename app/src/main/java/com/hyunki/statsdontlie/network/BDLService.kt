package com.hyunki.statsdontlie.network

import com.hyunki.statsdontlie.constants.BDLAppConstants
import com.hyunki.statsdontlie.model.BDLResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BDLService {
    /**
     *
     * @param player_ids
     * @param perPage
     * @param seasons
     * @return a single that will have one response.
     */
    @GET(BDLAppConstants.ENDPOINT)
    suspend fun getPlayerData(@Query("player_ids[]") player_ids: Int,
                      @Query("seasons[]") seasons: Int,
                      @Query("per_page") perPage: Int): BDLResponse
}