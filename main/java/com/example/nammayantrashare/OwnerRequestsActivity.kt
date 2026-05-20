package com.example.nammayantrashare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.adapters.BookingAdapter
import com.example.nammayantrashare.models.Booking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OwnerRequestsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var bookingList:
            ArrayList<Booking>

    private lateinit var adapter:
            BookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_owner_requests
        )

        recyclerView =
            findViewById(
                R.id.recyclerViewRequests
            )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        bookingList =
            ArrayList()

        adapter =
            BookingAdapter(
                this,
                bookingList
            )

        recyclerView.adapter =
            adapter

        loadRequests()
    }

    private fun loadRequests() {

        val ownerEmail =
            FirebaseAuth.getInstance()
                .currentUser?.email

        FirebaseFirestore.getInstance()
            .collection("bookings")
            .whereEqualTo(
                "ownerEmail",
                ownerEmail
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