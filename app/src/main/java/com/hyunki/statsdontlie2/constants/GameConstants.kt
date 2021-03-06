package com.hyunki.statsdontlie2.constants

import com.hyunki.statsdontlie2.view.fragments.game.utils.Question

object GameConstants {
    //player_ids for API call
    private const val JAMES_HARDEN = 192
    private const val PAUL_GEORGE = 172
    private const val GIANNIS_ANTETOKOUNMPO = 15
    private const val JOEL_EMBIID = 145
    private const val STEPHEN_CURRY = 115
    private const val KAWHI_LEONARD = 274
    private const val DEVIN_BOOKER = 57
    private const val KEVIN_DURANT = 140
    private const val DAMIAN_LILLARD = 278
    private const val KEMBA_WALKER = 465
    private const val BRADLEY_BEAL = 37
    private const val BLAKE_GRIFFIN = 189
    private const val KARL_ANTHONY_TOWNS = 447
    private const val KYRIE_IRVING = 228
    private const val DONOVAN_MITCHELL = 322
    private const val ZACH_LAVINE = 268
    private const val RUSSELL_WESTBROOK = 472
    private const val KLAY_THOMPSON = 443
    private const val JULIUS_RANDLE = 387
    private const val LAMARCUS_ALDRIDGE = 6
    private const val DEMAR_DEROZAN = 125
    private const val LEBRON_JAMES = 237
    private const val JRUE_HOLIDAY = 214
    private const val D_ANGELO_RUSSELL = 405
    private const val MIKE_CONLEY = 104

    val PLAYER_ARRAY_CONSTANTS = intArrayOf(JAMES_HARDEN, PAUL_GEORGE,
            GIANNIS_ANTETOKOUNMPO, JOEL_EMBIID, STEPHEN_CURRY, KAWHI_LEONARD, DEVIN_BOOKER,
            KEVIN_DURANT, DAMIAN_LILLARD, KEMBA_WALKER, BRADLEY_BEAL, BLAKE_GRIFFIN, KARL_ANTHONY_TOWNS,
            KYRIE_IRVING, DONOVAN_MITCHELL, ZACH_LAVINE, RUSSELL_WESTBROOK, KLAY_THOMPSON,
            JULIUS_RANDLE, LAMARCUS_ALDRIDGE, DEMAR_DEROZAN, LEBRON_JAMES, JRUE_HOLIDAY, D_ANGELO_RUSSELL)

    val QUESTIONS_ARRAY = arrayOf<Question>(
            Question("playerPointAvg","Who has a higher point-per-game average?"),
            Question("playerAssistAvg","Who has a higher assist-per-game average?"),
            Question("playerBlocksAvg","Who averages more blocks per game?"),
            Question("playerDefRebAvg","Who averages more defensive rebounds per game?"),
            Question("player3PM","Who makes more 3 point shots on average?"),
            Question("player3PA","Who attempts more 3 point shots on average?"))
}