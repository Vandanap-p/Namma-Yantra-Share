package com.example.nammayantrashare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.adapters.FarmerBookingAdapter
import com.example.nammayantrashare.models.Booking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FarmerBookingsActivity :
    AppCompatActivity() {

    private lateinit var recyclerView:
            RecyclerView

    private lateinit var bookingList:
            ArrayList<Booking>

    private lateinit var adapter:
            FarmerBookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_farmer_bookings
        )

        recyclerView =
            findViewById(
                R.id.recyclerViewBookings
            )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        bookingList =
            ArrayList()

        adapter =
            FarmerBookingAdapter(
                this,
                bookingList
            )

        recyclerView.adapter =
            adapter

        loadBookings()
    }

    private fun loadBookings() {

        val farmerEmail =
            FirebaseAuth.getInstance()
                .currentUser?.email

        FirebaseFirestore.getInstance()
            .collection("bookings")
            .whereEqualTo(
                "farmerEmail",
                farmerEmail
            )
            .addSnapshotListener { value, _ ->

                bookingList.clear()

                value?.documents?.forEach {

                    val booking =
                        it.toObject(
                            Booking::class.java
                        )

                    if(booking != null){

                        bookingList.add(booking)
                    }
                }

                adapter.notifyDataSetChanged()
            }
    }
}