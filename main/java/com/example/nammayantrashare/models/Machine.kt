package com.example.nammayantrashare.models

data class Machine(

    var id: String = "",

    var ownerName: String = "",

    var ownerEmail: String = "",

    var phone: String = "",

    var address: String = "",

    var machineName: String = "",

    var machineType: String = "",

    var hourlyRate: Long = 0,

    var dailyRate: Long = 0,

    var bookingStatus: String = "Available"
)