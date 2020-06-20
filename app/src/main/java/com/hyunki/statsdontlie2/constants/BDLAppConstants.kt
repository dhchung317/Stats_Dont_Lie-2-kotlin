package com.hyunki.statsdontlie2.constants

object BDLAppConstants {
    const val BASE_URL = "https://www.balldontlie.io"
    const val ENDPOINT = "/api/v1/stats"
    const val BDLREPOSITORY_TAG = "BDLRepository"
    const val MAIN_ACTIVITY_TAG = "MainActivity"
    const val EXAMPLE_UNIT_TEST_TAG = "ExampleUnitTest"

    //Result Fragment Constants
    const val CORRECT = "CORRECT"
    const val WRONG = "WRONG"
    const val SHARED_PREFS = "com.example.statsdontlie.sharedPrefsKey"
    const val PLAYER_KEY_SHARED_PREFS = "com.example.statsdontlie.playerAverageModelListKey"

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
    val QUESTIONS_ARRAY = arrayOf<String?>(
            "Who has a higher point-per-game average?",
            "Who has a higher assist-per-game average?",
            "Who averages more blocks per game?",
            "Who averages more defensive rebounds per game?",
            "Who makes more 3 point shots on average?",
            "Who attempts more 3 point shots on average?")
}