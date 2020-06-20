package com.example.statsdontlie.utils

import java.util.*

object RandomNumberGenerator {
    private var random1 = 0
    val randomNumber1: Int
        get() {
            val random = Random()
            random1 = random.nextInt(24)
            return random1
        }

    val randomNumber2: Int
        get() {
            val random = Random()
            var random2 = random.nextInt(24)
            while (random2 == random1) random2 = random.nextInt(24)
            return random2
        }

    val randomNumber: Int
        get() {
            val random = Random()
            random1 = random.nextInt(6)
            return random1
        }
}