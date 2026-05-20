package com.example.nammayantrashare.notifications

import com.google.firebase.messaging.FirebaseMessaging

object NotificationHelper {

    fun subscribeToBookings(){

        FirebaseMessaging.getInstance()
            .subscribeToTopic(
                "bookings"
            )
    }

    fun subscribeToMachines(){

        FirebaseMessaging.getInstance()
            .subscribeToTopic(
                "machines"
            )
    }
}