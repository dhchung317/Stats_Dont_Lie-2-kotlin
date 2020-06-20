package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.model.NBAPlayer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object PlayerAverageModelConverter {
    fun playerAverageSerializer(NBAPlayers: List<NBAPlayer?>?): String {
        return Gson().toJson(NBAPlayers)
    }

    fun playerAverageDeserializer(json: String?): List<NBAPlayer> {
        if (json == null || json == "") return emptyList()
        val listType = object : TypeToken<ArrayList<NBAPlayer?>?>() {}.type
        return Gson().fromJson(json, listType)
    }
}