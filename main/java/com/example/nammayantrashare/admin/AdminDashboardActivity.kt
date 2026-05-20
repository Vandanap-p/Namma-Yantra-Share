package com.example.nammayantrashare.admin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nammayantrashare.R
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardActivity :
    AppCompatActivity() {

    private lateinit var tvUsers:
            TextView

    private lateinit var tvMachines:
            TextView

    private lateinit var tvBookings:
            TextView

    private lateinit var tvReviews:
            TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_admin_dashboard
        )

        tvUsers =
            findViewById(
                R.id.tvUsers
            )

        tvMachines =
            findViewById(
                R.id.tvMachines
            )

        tvBookings =
            findViewById(
                R.id.tvBookings
            )

        tvReviews =
            findViewById(
                R.id.tvReviews
            )

        loadCounts()
    }

    private fun loadCounts() {

        val firestore =
            FirebaseFirestore.getInstance()

        // USERS

        firestore.collection("users")
            .get()
            .addOnSuccessListener {

                tvUsers.text =
                    "Total Users: ${it.size()}"
            }

        // MACHINES

        firestore.collection("machines")
            .get()
            .addOnSuccessListener {

                tvMachines.text =
                    "Total Machines: ${it.size()}"
            }

        // BOOKINGS

        firestore.collection("bookings")
            .get()
            .addOnSuccessListener {

                tvBookings.text =
                    "Total Bookings: ${it.size()}"
            }

        // REVIEWS

        firestore.collection("reviews")
            .get()
            .addOnSuccessListener {

                tvReviews.text =
                    "Total Reviews: ${it.size()}"
            }
    }
}