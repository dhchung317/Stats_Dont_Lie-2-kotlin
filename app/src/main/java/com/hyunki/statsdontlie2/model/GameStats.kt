package com.hyunki.statsdontlie2.model

data class GameStats (
        val pts: Int = 0,
        val fg_pct: Double = 0.0,
        val fg3_pct: Double = 0.0,
        val dreb: Int = 0,
        val blk: Int = 0,
        val ast: Int = 0,
        val fg3a: Int = 0,
        val fg3m: Int = 0,
        val player: Player
)