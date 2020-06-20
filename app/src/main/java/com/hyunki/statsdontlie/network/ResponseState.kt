package com.hyunki.statsdontlie.network

import com.hyunki.statsdontlie.model.PlayerAverageModel

sealed class ResponseState {
    object Loading: ResponseState()

    data class Error(val e: String): ResponseState()

    sealed class Success: ResponseState() {
        data class OnResponsesLoaded(
                val players: List<PlayerAverageModel>
        ): Success()
    }
}