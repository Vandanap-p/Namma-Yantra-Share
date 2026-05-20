package com.example.nammayantrashare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nammayantrashare.models.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_review
        )

        val machineId =
            intent.getStringExtra(
                "machineId"
            ) ?: ""

        val machineName =
            intent.getStringExtra(
                "machineName"
            ) ?: ""

        val ownerEmail =
            intent.getStringExtra(
                "ownerEmail"
            ) ?: ""

        val ratingBar =
            findViewById<RatingBar>(
                R.id.ratingBar
            )

        val etReview =
            findViewById<EditText>(
                R.id.etReview
            )

        val btnSubmitReview =
            findViewById<Button>(
                R.id.btnSubmitReview
            )

        btnSubmitReview.setOnClickListener {

            val reviewText =
                etReview.text.toString()

            if(reviewText.isEmpty()){

                Toast.makeText(
                    this,
                    "Enter Review",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val firestore =
                FirebaseFirestore.getInstance()

            val reviewId =
                firestore.collection("reviews")
                    .document()
                    .id

            val review = Review(

                reviewId = reviewId,

                machineId = machineId,

                machineName = machineName,

                ownerEmail = ownerEmail,

                farmerEmail =
                    FirebaseAuth.getInstance()
                        .currentUser?.email ?: "",

                rating =
                    ratingBar.rating,

                review =
                    reviewText
            )

            firestore.collection("reviews")
                .document(reviewId)
                .set(review)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Review Submitted Successfully\nThank You",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }
        }
    }
}