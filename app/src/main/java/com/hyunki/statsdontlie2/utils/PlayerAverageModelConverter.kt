package com.hyunki.statsdontlie2.utils

import com.hyunki.statsdontlie2.model.PlayerAverageModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object PlayerAverageModelConverter {
    fun playerAverageSerializer(playerAverageModels: List<PlayerAverageModel?>?): String {
        return Gson().toJson(playerAverageModels)
    }

    fun playerAverageDeserializer(json: String?): List<PlayerAverageModel> {
        if (json == null || json == "") return emptyList()
        val listType = object : TypeToken<ArrayList<PlayerAverageModel?>?>() {}.type
        return Gson().fromJson(json, listType)
    }
}