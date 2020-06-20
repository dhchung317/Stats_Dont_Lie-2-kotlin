package com.hyunki.statsdontlie2.network

import com.hyunki.statsdontlie2.model.NBAPlayer

sealed class ResponseState {
    object Loading: ResponseState()

    data class Error(val e: String): ResponseState()

    sealed class Success: ResponseState() {
        data class OnResponsesLoaded(
                val NBAPlayers: List<NBAPlayer>
        ): Success()
    }
}