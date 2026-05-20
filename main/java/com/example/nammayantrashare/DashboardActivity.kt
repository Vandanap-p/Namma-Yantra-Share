package com.example.nammayantrashare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.nammayantrashare.admin.AdminDashboardActivity
import com.example.nammayantrashare.notifications.NotificationHelper
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        NotificationHelper.subscribeToBookings()

        NotificationHelper.subscribeToMachines()

        val btnAddMachine =
            findViewById<Button>(
                R.id.btnAddMachine
            )

        val btnViewMachines =
            findViewById<Button>(
                R.id.btnViewMachines
            )

        val btnRequests =
            findViewById<Button>(
                R.id.btnRequests
            )

        val btnMyBookings =
            findViewById<Button>(
                R.id.btnMyBookings
            )

        val btnAdmin =
            findViewById<Button>(
                R.id.btnAdmin
            )

        val btnLogout =
            findViewById<Button>(
                R.id.btnLogout
            )

        btnAddMachine.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddMachineActivity::class.java
                )
            )
        }

        btnViewMachines.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ViewMachinesActivity::class.java
                )
            )
        }

        btnRequests.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    OwnerRequestsActivity::class.java
                )
            )
        }

        btnMyBookings.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    FarmerBookingsActivity::class.java
                )
            )
        }

        btnAdmin.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AdminDashboardActivity::class.java
                )
            )
        }

        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance()
                .signOut()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}