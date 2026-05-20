package com.example.nammayantrashare.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.R
import com.example.nammayantrashare.models.Booking
import com.google.firebase.firestore.FirebaseFirestore

class BookingAdapter(

    private val context: Context,

    private val bookingList: ArrayList<Booking>

) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvMachine =
            itemView.findViewById<TextView>(
                R.id.tvMachine
            )

        val tvFarmer =
            itemView.findViewById<TextView>(
                R.id.tvFarmer
            )

        val tvPhone =
            itemView.findViewById<TextView>(
                R.id.tvPhone
            )

        val tvAddress =
            itemView.findViewById<TextView>(
                R.id.tvAddress
            )

        val tvDate =
            itemView.findViewById<TextView>(
                R.id.tvDate
            )

        val tvTime =
            itemView.findViewById<TextView>(
                R.id.tvTime
            )

        val tvPrice =
            itemView.findViewById<TextView>(
                R.id.tvPrice
            )

        val tvStatus =
            itemView.findViewById<TextView>(
                R.id.tvStatus
            )

        val btnAccept =
            itemView.findViewById<Button>(
                R.id.btnAccept
            )

        val btnReject =
            itemView.findViewById<Button>(
                R.id.btnReject
            )

        val btnOwnerConfirm =
            itemView.findViewById<Button>(
                R.id.btnOwnerConfirm
            )

        val btnWorkDone =
            itemView.findViewById<Button>(
                R.id.btnWorkDone
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.booking_item,
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

        holder.tvFarmer.text =
            "Farmer: ${booking.farmerName}"

        holder.tvPhone.text =
            "Phone: ${booking.farmerPhone}"

        holder.tvAddress.text =
            "Address: ${booking.farmerAddress}"

        holder.tvDate.text =
            "Date: ${booking.bookingDate}"

        holder.tvTime.text =
            "${booking.startTime} - ${booking.endTime}"

        holder.tvPrice.text =
            "Total Amount: ₹${booking.totalPrice}"

        holder.btnAccept.visibility =
            View.GONE

        holder.btnReject.visibility =
            View.GONE

        holder.btnOwnerConfirm.visibility =
            View.GONE

        holder.btnWorkDone.visibility =
            View.GONE

        // PENDING

        if(booking.status == "Pending"){

            holder.tvStatus.text =
                "New Booking Request"

            holder.tvStatus.setTextColor(
                Color.BLACK
            )

            holder.btnAccept.visibility =
                View.VISIBLE

            holder.btnReject.visibility =
                View.VISIBLE
        }

        // ACCEPTED

        if(booking.status == "Accepted"){

            holder.tvStatus.text =
                "Waiting Farmer Confirmation"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )
        }

        // REJECTED

        if(booking.status == "Rejected"){

            holder.tvStatus.text =
                "Booking Rejected"

            holder.tvStatus.setTextColor(
                Color.RED
            )
        }

        // FARMER CONFIRMED

        if(booking.status == "Farmer Confirmed"){

            holder.tvStatus.text =
                "Farmer Confirmed"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )
        }

        // PAYMENT SELECTED

        if(
            booking.paymentMethod.isNotEmpty()
            &&
            !booking.ownerConfirmed
        ){

            holder.tvStatus.text =
                "Payment Selected:\n${booking.paymentMethod}"

            holder.tvStatus.setTextColor(
                Color.MAGENTA
            )

            holder.btnOwnerConfirm.visibility =
                View.VISIBLE
        }

        // OWNER CONFIRMED

        if(booking.ownerConfirmed){

            holder.tvStatus.text =
                "Booking Fully Confirmed"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            holder.btnWorkDone.visibility =
                View.VISIBLE
        }

        // WORK DONE

        if(booking.workDone){

            holder.tvStatus.text =
                "Work Completed\nReview Pending"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            holder.btnWorkDone.visibility =
                View.GONE
        }

        // REVIEW SUBMITTED

        if(booking.reviewSubmitted){

            holder.tvStatus.text =
                "Review Submitted By Farmer\nWork Completed"

            holder.tvStatus.setTextColor(
                Color.BLUE
            )

            holder.btnAccept.visibility =
                View.GONE

            holder.btnReject.visibility =
                View.GONE

            holder.btnOwnerConfirm.visibility =
                View.GONE

            holder.btnWorkDone.visibility =
                View.GONE
        }

        // ACCEPT BUTTON

        holder.btnAccept.setOnClickListener {

            FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(booking.bookingId)
                .update(
                    "status",
                    "Accepted"
                )

            Toast.makeText(
                context,
                "Booking Accepted",
                Toast.LENGTH_SHORT
            ).show()
        }

        // REJECT BUTTON

        holder.btnReject.setOnClickListener {

            FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(booking.bookingId)
                .update(
                    "status",
                    "Rejected"
                )

            Toast.makeText(
                context,
                "Booking Rejected",
                Toast.LENGTH_SHORT
            ).show()
        }

        // OWNER CONFIRM

        holder.btnOwnerConfirm.setOnClickListener {

            FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(booking.bookingId)
                .update(
                    "ownerConfirmed",
                    true
                )

            Toast.makeText(
                context,
                "Booking Fully Confirmed",
                Toast.LENGTH_SHORT
            ).show()
        }

        // WORK DONE

        holder.btnWorkDone.setOnClickListener {

            FirebaseFirestore.getInstance()
                .collection("bookings")
                .document(booking.bookingId)
                .update(
                    "workDone",
                    true
                )

            Toast.makeText(
                context,
                "Work Completed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}