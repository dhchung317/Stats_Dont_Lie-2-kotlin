package com.hyunki.statsdontlie2.model

import com.hyunki.sql.SavedNBAPlayer

data class NBAPlayer(val playerID: Long,
                     val firstName: String,
                     val lastName: String,
                     val image: String,
                     val playerPointAvg: Double,
                     val playerAssistAvg: Double,
                     val playerBlocksAvg: Double,
                     val playerDefRebAvg: Double,
                     val player3PM: Double,
                     val player3PA: Double) {

    fun getStat(position: Int): Double {
        return when (position) {
            0 -> playerPointAvg
            1 -> playerAssistAvg
            2 -> playerBlocksAvg
            3 -> playerDefRebAvg
            4 -> player3PM
            5 -> player3PA
            else -> 0.0
        }
    }

    override fun toString(): String {
        return "PlayerAverageModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", playerPointAvg=" + playerPointAvg +
                ", playerAssistAvg=" + playerAssistAvg +
                ", playerBlocksAvg=" + playerBlocksAvg +
                ", playerDefRebAvg=" + playerDefRebAvg +
                ", player3PM=" + player3PM +
                ", player3PA=" + player3PA +
                '}'
    }

    companion object {
        val EMPTY: NBAPlayer
            get() = NBAPlayer(1, "", "", "", 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0)
    }

}