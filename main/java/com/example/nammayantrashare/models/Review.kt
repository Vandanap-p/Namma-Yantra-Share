package com.example.nammayantrashare.models

data class Review(

    var reviewId: String = "",

    var machineId: String = "",

    var machineName: String = "",

    var ownerEmail: String = "",

    var farmerEmail: String = "",

    var rating: Float = 0f,

    var review: String = "",

    var timestamp: Long =
        System.currentTimeMillis()
)