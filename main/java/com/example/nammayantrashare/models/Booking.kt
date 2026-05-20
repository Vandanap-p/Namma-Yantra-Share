package com.example.nammayantrashare.models

data class Booking(

    var bookingId: String = "",

    var machineId: String = "",

    var machineName: String = "",

    var machineType: String = "",

    // OWNER DETAILS

    var ownerName: String = "",

    var ownerEmail: String = "",

    var ownerPhone: String = "",

    var ownerAddress: String = "",

    // FARMER DETAILS

    var farmerName: String = "",

    var farmerEmail: String = "",

    var farmerPhone: String = "",

    var farmerAddress: String = "",

    // BOOKING DETAILS

    var bookingDate: String = "",

    var startTime: String = "",

    var endTime: String = "",

    var duration: Long = 0,

    var totalPrice: Long = 0,

    // WORKFLOW STATUS

    var status: String = "Pending",

    // PAYMENT

    var paymentMethod: String = "",

    // FINAL FLOW FLAGS

    var ownerConfirmed: Boolean = false,

    var workDone: Boolean = false,

    var reviewSubmitted: Boolean = false
)