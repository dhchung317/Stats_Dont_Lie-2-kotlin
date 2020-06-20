package com.example.statsdontlie.network

import com.example.statsdontlie.constants.BDLAppConstants
import com.example.statsdontlie.model.BDLResponse
import io.reactivex.Single
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
    fun getPlayerData(@Query("player_ids[]") player_ids: Int,
                      @Query("seasons[]") seasons: Int,
                      @Query("per_page") perPage: Int): Single<BDLResponse?>?
}