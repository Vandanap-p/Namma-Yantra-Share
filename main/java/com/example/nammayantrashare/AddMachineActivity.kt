package com.example.nammayantrashare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nammayantrashare.models.Machine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddMachineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_machine)

        val etOwnerName =
            findViewById<EditText>(R.id.etOwnerName)

        val etPhone =
            findViewById<EditText>(R.id.etPhone)

        val etAddress =
            findViewById<EditText>(R.id.etAddress)

        val etMachineName =
            findViewById<EditText>(R.id.etMachineName)

        val etMachineType =
            findViewById<EditText>(R.id.etMachineType)

        val etHourlyRate =
            findViewById<EditText>(R.id.etHourlyRate)

        val etDailyRate =
            findViewById<EditText>(R.id.etDailyRate)

        val btnSaveMachine =
            findViewById<Button>(R.id.btnSaveMachine)

        btnSaveMachine.setOnClickListener {

            val firestore =
                FirebaseFirestore.getInstance()

            val machineId =
                firestore.collection("machines")
                    .document()
                    .id

            val machine = Machine(

                id = machineId,

                ownerName =
                    etOwnerName.text.toString(),

                ownerEmail =
                    FirebaseAuth.getInstance()
                        .currentUser?.email ?: "",

                phone =
                    etPhone.text.toString(),

                address =
                    etAddress.text.toString(),

                machineName =
                    etMachineName.text.toString(),

                machineType =
                    etMachineType.text.toString(),

                hourlyRate =
                    etHourlyRate.text.toString()
                        .toLongOrNull() ?: 0,

                dailyRate =
                    etDailyRate.text.toString()
                        .toLongOrNull() ?: 0
            )

            firestore.collection("machines")
                .document(machineId)
                .set(machine)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Machine Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
        }
    }
}