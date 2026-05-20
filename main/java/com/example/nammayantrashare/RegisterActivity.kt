package com.example.nammayantrashare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val etEmail =
            findViewById<EditText>(
                R.id.etEmail
            )

        val etPassword =
            findViewById<EditText>(
                R.id.etPassword
            )

        val btnRegister =
            findViewById<Button>(
                R.id.btnRegister
            )

        btnRegister.setOnClickListener {

            val email =
                etEmail.text.toString()

            val password =
                etPassword.text.toString()

            if(email.isEmpty()
                ||
                password.isEmpty()
            ){

                Toast.makeText(
                    this,
                    "Enter All Fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                    email,
                    password
                )
                .addOnSuccessListener {

                    val currentUser =
                        FirebaseAuth.getInstance()
                            .currentUser

                    val userMap =
                        hashMapOf(

                            "uid" to currentUser?.uid,

                            "email" to email
                        )

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(currentUser!!.uid)
                        .set(userMap)

                    Toast.makeText(
                        this,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}