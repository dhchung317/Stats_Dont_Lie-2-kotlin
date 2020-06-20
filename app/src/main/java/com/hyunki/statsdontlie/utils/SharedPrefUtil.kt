package com.hyunki.statsdontlie.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.hyunki.statsdontlie.constants.BDLAppConstants
import com.hyunki.statsdontlie.model.PlayerAverageModel

class SharedPrefUtil(application: Application) {
    companion object {
        var sharedPreferences: SharedPreferences? = null
        fun savePlayerAverageModelList(playerAverageModels: List<PlayerAverageModel?>?) {
            sharedPreferences!!.edit().putString(BDLAppConstants.PLAYER_KEY_SHARED_PREFS, PlayerAverageModelConverter.playerAverageSerializer(playerAverageModels))
                    .apply()
        }

        fun checkSharedPrefs(): Boolean {
            return sharedPreferences!!.getString(BDLAppConstants.PLAYER_KEY_SHARED_PREFS, "") == ""
        }

    }

    init {
        if (sharedPreferences == null) {
            sharedPreferences = application.getSharedPreferences(BDLAppConstants.SHARED_PREFS, Context.MODE_PRIVATE)
        }
    }
}