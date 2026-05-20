package com.example.nammayantrashare.ai

object PricePrediction {

    fun predictPrice(
        baseRate: Long,
        demand: Int,
        season: Int
    ): Long {

        return baseRate +
                (demand * 50) +
                (season * 100)
    }
}
