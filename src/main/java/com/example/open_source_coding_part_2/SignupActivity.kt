package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.EditText


class SignupActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inside onCreate()
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val btnSignup = findViewById<Button>(R.id.btnSignup)
        btnSignup?.setOnClickListener {
            val intent = Intent(this, Financial_education::class.java)
            startActivity(intent)

            val btnLogout = findViewById<Button>(R.id.btnLogout)
            btnLogout?.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

            val usernameInput = findViewById<EditText>(R.id.editTextText)
            val passwordInput = findViewById<EditText>(R.id.editTextText3)
            val confirmPasswordInput = findViewById<EditText>(R.id.editTextText4)
            val submitButton = findViewById<Button>(R.id.btnsignup)

            submitButton.setOnClickListener {
                val name = usernameInput.text.toString().trim()
                val password = passwordInput.text.toString().trim()
                val confirmPassword = confirmPasswordInput.text.toString().trim()

                when {
                    name.isEmpty() -> usernameInput.error = "Username cannot be empty"
                    password.isEmpty() -> passwordInput.error = "Password cannot be empty"
                    confirmPassword.isEmpty() -> confirmPasswordInput.error =
                        "Confirm your password"

                    password != confirmPassword -> confirmPasswordInput.error =
                        "Passwords do not match"

                    else -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val user = User(username = name, password = password)
                                userDao.insertUser(user)
                                Log.d("SignupActivity", "User inserted: $user")

                                val fetchedUser = userDao.getUserByUsername(name)
                                if (fetchedUser != null) {
                                    Log.d(
                                        "SignupActivity",
                                        "User retrieved: ${fetchedUser.username}"
                                    )
                                } else {
                                    Log.e("SignupActivity", "Failed to retrieve user after insert.")
                                }
                            } catch (e: Exception) {
                                Log.e("SignupActivity", "Error inserting user: ${e.message}")
                            }
                        }

                        val intent = Intent(this, CategoryActivity::class.java)
                        intent.putExtra("USERNAME", name)
                        startActivity(intent)
                        finish()
                    }
                }

            }
        }
    }}