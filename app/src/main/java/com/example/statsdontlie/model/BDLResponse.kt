package com.example.statsdontlie.model

import com.google.gson.annotations.SerializedName

class BDLResponse {
    val data: List<GameStats?>? = null

    inner class GameStats {
        val pts = 0
        val fg_pct = 0.0
        val fg3_pct = 0.0
        val dreb = 0
        val blk = 0
        val ast = 0
        val fg3a = 0
        val fg3m = 0
        val player: Player? = null

        inner class Player {
            val id = 0

            @SerializedName("first_name")
            val firstName: String? = null

            @SerializedName("last_name")
            val lastName: String? = null

        }
    }
}