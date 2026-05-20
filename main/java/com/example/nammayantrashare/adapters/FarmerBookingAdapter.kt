package com.example.nammayantrashare.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.R
import com.example.nammayantrashare.ReviewActivity
import com.example.nammayantrashare.models.Booking
import com.google.firebase.firestore.FirebaseFirestore

class FarmerBookingAdapter(

    private val context: Context,

    private val bookingList: ArrayList<Booking>

) : RecyclerView.Adapter<FarmerBookingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvMachine =
            itemView.findViewById<TextView>(
                R.id.tvMachine
            )

        val tvOwner =
            itemView.findViewById<TextView>(
                R.id.tvOwner
            )

        val tvPhone =
            itemView.findViewById<TextView>(
                R.id.tvPhone
            )

        val tvAddress =
            itemView.findViewById<TextView>(
                R.id.tvAddress
            )

        val tvPrice =
            itemView.findViewById<TextView>(
                R.id.tvPrice
            )

        val tvStatus =
            itemView.findViewById<TextView>(
                R.id.tvStatus
            )

        val btnConfirm =
            itemView.findViewById<Button>(
                R.id.btnConfirm
            )

        val btnPayment =
            itemView.findViewById<Button>(
                R.id.btnPayment
            )

        val btnReview =
            itemView.findViewById<Button>(
                R.id.btnReview
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.farmer_booking_item,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return bookingList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val booking =
            bookingList[position]

        holder.tvMachine.text =
            booking.machineName

        holder.tvOwner.text =
            "Owner: ${booking.ownerName}"

        holder.tvPhone.text =
            "Phone: ${booking.ownerPhone}"

        holder.tvAddress.text =
            "Address: ${booking.ownerAddress}"

        holder.tvPrice.text =
            "Total Amount: ₹${booking.totalPrice}"

        holder.btnConfirm.visibility =
            View.GONE

        holder.btnPayment.visibility =
            View.GONE

        holder.btnReview.visibility =
            View.GONE

        // PENDING

        if(booking.status == "Pending"){

            holder.tvStatus.text =
                "Booking Sent\nPending"

            holder.tvStatus.setTextColor(
                Color.BLACK
            )
        }

        // ACCEPTED

        if(booking.status == "Accepted"){

            holder.tvStatus.text =
                "Owner Accepted\nConfirm Booking"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            holder.btnConfirm.visibility =
                View.VISIBLE
        }

        // REJECTED

        if(booking.status == "Rejected"){

            holder.tvStatus.text =
                "Booking Rejected"

            holder.tvStatus.setTextColor(
                Color.RED
            )
        }

        // FARMER CONFIRM

        holder.btnConfirm.setOnClickListener {

            FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(booking.bookingId)
                .update(
                    "status",
                    "Farmer Confirmed"
                )

            Toast.makeText(
                context,
                "Booking Confirmed",
                Toast.LENGTH_SHORT
            ).show()
        }

        // FARMER CONFIRMED

        if(booking.status == "Farmer Confirmed"){

            holder.tvStatus.text =
                "Booking Confirmed\nSelect Payment"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            holder.btnPayment.visibility =
                View.VISIBLE
        }

        // PAYMENT

        holder.btnPayment.setOnClickListener {

            val options =
                arrayOf(
                    "Online Pay",
                    "Pay After Work"
                )

            AlertDialog.Builder(context)
                .setTitle(
                    "Select Payment"
                )
                .setItems(options){ _, which ->

                    val selected =
                        options[which]

                    FirebaseFirestore.getInstance()
                        .collection("bookings")
                        .document(booking.bookingId)
                        .update(
                            "paymentMethod",
                            selected
                        )

                    Toast.makeText(
                        context,
                        "Payment Selected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .show()
        }

        // WAITING OWNER CONFIRM

        if(
            booking.paymentMethod.isNotEmpty()
            &&
            !booking.ownerConfirmed
        ){

            holder.tvStatus.text =
                "Payment: ${booking.paymentMethod}\nWaiting Owner Confirmation"

            holder.tvStatus.setTextColor(
                Color.MAGENTA
            )
        }

        // OWNER CONFIRMED

        if(booking.ownerConfirmed){

            holder.tvStatus.text =
                "Booking Fully Confirmed\nContact Owner"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )
        }

        // WORK DONE

        if(booking.workDone){

            holder.tvStatus.text =
                "Work Completed\nSend Review"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            if(!booking.reviewSubmitted){

                holder.btnReview.visibility =
                    View.VISIBLE
            }
        }

        // REVIEW SUBMITTED

        if(booking.reviewSubmitted){

            holder.tvStatus.text =
                "Review Submitted Successfully\nThank You"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            holder.btnConfirm.visibility =
                View.GONE

            holder.btnPayment.visibility =
                View.GONE

            holder.btnReview.visibility =
                View.GONE
        }

        // REVIEW BUTTON

        holder.btnReview.setOnClickListener {

            val intent =
                Intent(
                    context,
                    ReviewActivity::class.java
                )

            intent.putExtra(
                "machineId",
                booking.machineId
            )

            intent.putExtra(
                "machineName",
                booking.machineName
            )

            intent.putExtra(
                "ownerEmail",
                booking.ownerEmail
            )

            context.startActivity(intent)

            FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(booking.bookingId)
                .update(
                    "reviewSubmitted",
                    true
                )
        }
    }
}