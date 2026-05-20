package com.example.nammayantrashare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nammayantrashare.models.Booking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_booking)

        val machineId =
            intent.getStringExtra("machineId") ?: ""

        val machineName =
            intent.getStringExtra("machineName") ?: ""

        val machineType =
            intent.getStringExtra("machineType") ?: ""

        val ownerName =
            intent.getStringExtra("ownerName") ?: ""

        val ownerEmail =
            intent.getStringExtra("ownerEmail") ?: ""

        val ownerPhone =
            intent.getStringExtra("ownerPhone") ?: ""

        val ownerAddress =
            intent.getStringExtra("ownerAddress") ?: ""

        val hourlyRate =
            intent.getLongExtra(
                "hourlyRate",
                0
            )

        val tvMachineName =
            findViewById<TextView>(
                R.id.tvMachineName
            )

        val etFarmerName =
            findViewById<EditText>(
                R.id.etFarmerName
            )

        val etFarmerPhone =
            findViewById<EditText>(
                R.id.etFarmerPhone
            )

        val etFarmerAddress =
            findViewById<EditText>(
                R.id.etFarmerAddress
            )

        val etBookingDate =
            findViewById<EditText>(
                R.id.etBookingDate
            )

        val etStartTime =
            findViewById<EditText>(
                R.id.etStartTime
            )

        val etEndTime =
            findViewById<EditText>(
                R.id.etEndTime
            )

        val tvPrice =
            findViewById<TextView>(
                R.id.tvPrice
            )

        val btnPredictPrice =
            findViewById<Button>(
                R.id.btnPredictPrice
            )

        val btnBook =
            findViewById<Button>(
                R.id.btnBook
            )

        var calculatedHours = 0L

        var calculatedPrice = 0L

        tvMachineName.text =
            machineName

        btnPredictPrice.setOnClickListener {

            try {

                val start =
                    etStartTime.text.toString()

                val end =
                    etEndTime.text.toString()

                val format =
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    )

                val startTime =
                    format.parse(start)

                val endTime =
                    format.parse(end)

                val difference =
                    endTime.time -
                            startTime.time

                calculatedHours =
                    difference /
                            (1000 * 60 * 60)

                calculatedPrice =
                    calculatedHours *
                            hourlyRate

                tvPrice.text =
                    "Total Hours: $calculatedHours\nTotal Amount: ₹$calculatedPrice"

            } catch (e: Exception){

                Toast.makeText(
                    this,
                    "Enter valid time in HH:mm",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnBook.setOnClickListener {

            if(calculatedHours <= 0){

                Toast.makeText(
                    this,
                    "Please Predict Price First",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val bookingId =
                FirebaseFirestore
                    .getInstance()
                    .collection("bookings")
                    .document()
                    .id

            val booking = Booking(

                bookingId = bookingId,

                machineId = machineId,

                machineName = machineName,

                machineType = machineType,

                ownerName = ownerName,

                ownerEmail = ownerEmail,

                ownerPhone = ownerPhone,

                ownerAddress = ownerAddress,

                farmerName =
                    etFarmerName.text.toString(),

                farmerEmail =
                    FirebaseAuth.getInstance()
                        .currentUser?.email ?: "",

                farmerPhone =
                    etFarmerPhone.text.toString(),

                farmerAddress =
                    etFarmerAddress.text.toString(),

                bookingDate =
                    etBookingDate.text.toString(),

                startTime =
                    etStartTime.text.toString(),

                endTime =
                    etEndTime.text.toString(),

                duration =
                    calculatedHours,

                totalPrice =
                    calculatedPrice,

                status = "Pending"
            )

            FirebaseFirestore
                .getInstance()
                .collection("bookings")
                .document(bookingId)
                .set(booking)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Booking Request Sent",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
        }
    }
}