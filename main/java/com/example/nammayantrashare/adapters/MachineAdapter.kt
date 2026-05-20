package com.example.nammayantrashare.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.BookingActivity
import com.example.nammayantrashare.R
import com.example.nammayantrashare.models.Machine
import com.google.firebase.firestore.FirebaseFirestore

class MachineAdapter(

    private val context: Context,

    private val machineList: ArrayList<Machine>

) : RecyclerView.Adapter<MachineAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvMachineName =
            itemView.findViewById<TextView>(
                R.id.tvMachineName
            )

        val tvMachineType =
            itemView.findViewById<TextView>(
                R.id.tvMachineType
            )

        val tvMachineRate =
            itemView.findViewById<TextView>(
                R.id.tvMachineRate
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

        val tvReview =
            itemView.findViewById<TextView>(
                R.id.tvReview
            )

        val btnBookNow =
            itemView.findViewById<Button>(
                R.id.btnBookNow
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.machine_item,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return machineList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val machine =
            machineList[position]

        holder.tvMachineName.text =
            machine.machineName

        holder.tvMachineType.text =
            "Type: ${machine.machineType}"

        holder.tvMachineRate.text =
            "₹${machine.hourlyRate}/hour"

        holder.tvOwner.text =
            "Owner: ${machine.ownerName}"

        holder.tvPhone.text =
            "Phone: ${machine.phone}"

        holder.tvAddress.text =
            "Address: ${machine.address}"

        FirebaseFirestore.getInstance()
            .collection("reviews")
            .whereEqualTo(
                "machineId",
                machine.id
            )
            .get()
            .addOnSuccessListener { documents ->

                if(documents.isEmpty){

                    holder.tvReview.text =
                        "No Reviews Yet"

                }else{

                    var allReviews = ""

                    for(document in documents){

                        val reviewText =
                            document.getString(
                                "review"
                            ) ?: ""

                        val rating =
                            document.getDouble(
                                "rating"
                            ) ?: 0.0

                        val farmer =
                            document.getString(
                                "farmerEmail"
                            ) ?: ""

                        allReviews +=
                            "⭐ $rating\n$reviewText\nBy: $farmer\n\n"
                    }

                    holder.tvReview.text =
                        allReviews
                }
            }

        holder.btnBookNow.setOnClickListener {

            val intent =
                Intent(
                    context,
                    BookingActivity::class.java
                )

            intent.putExtra(
                "machineId",
                machine.id
            )

            intent.putExtra(
                "machineName",
                machine.machineName
            )

            intent.putExtra(
                "machineType",
                machine.machineType
            )

            intent.putExtra(
                "ownerName",
                machine.ownerName
            )

            intent.putExtra(
                "ownerEmail",
                machine.ownerEmail
            )

            intent.putExtra(
                "ownerPhone",
                machine.phone
            )

            intent.putExtra(
                "ownerAddress",
                machine.address
            )

            intent.putExtra(
                "hourlyRate",
                machine.hourlyRate
            )

            context.startActivity(intent)
        }
    }
}