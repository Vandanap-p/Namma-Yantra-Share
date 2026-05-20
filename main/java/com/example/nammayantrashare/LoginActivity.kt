package com.example.nammayantrashare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val btnRegister =
            findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {

            val email =
                etEmail.text.toString()

            val password =
                etPassword.text.toString()

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(
                    email,
                    password
                )
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this,
                            DashboardActivity::class.java
                        )
                    )

                    finish()
                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        btnRegister.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
}