package com.hyunki.statsdontlie2.model

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

    override fun toString(): String {

        return """
               |PlayerAverageModel {
               |   firstName = $firstName
               |   lastName = $lastName 
               |   playerPointAvg = $playerPointAvg
               |   playerAssistAvg = $playerAssistAvg
               |   playerBlocksAvg = $playerBlocksAvg
               |   playerDefRebAvg = $playerDefRebAvg
               |   player3PM = $player3PM
               |   player3PA = $player3PA
               |}
               """.trimMargin()
    }
}