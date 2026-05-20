package com.example.nammayantrashare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.R
import com.example.nammayantrashare.models.Review

class ReviewAdapter(

    private val context: Context,

    private val reviewList: ArrayList<Review>

) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvUser =
            itemView.findViewById<TextView>(R.id.tvUser)

        val tvRating =
            itemView.findViewById<TextView>(R.id.tvRating)

        val tvReview =
            itemView.findViewById<TextView>(R.id.tvReview)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.review_item,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return reviewList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val review =
            reviewList[position]

        holder.tvUser.text =
            review.farmerEmail

        holder.tvRating.text =
            "Rating: ${review.rating}"

        holder.tvReview.text =
            review.review
    }
}