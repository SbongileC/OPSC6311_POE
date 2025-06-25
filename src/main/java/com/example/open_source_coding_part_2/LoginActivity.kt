package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        fun add_user(view: View) {

            //collecting the user's info

            val username: EditText = findViewById(R.id.editTextText)
            val password: EditText = findViewById(R.id.editTextText3)

            //creating an instance for the firestore database
            val database = FirebaseFirestore.getInstance()

            val db = AppDatabase.getDatabase(this)
            val userDao = db.userDao()

            val btnLogin = findViewById<Button>(R.id.btnLogin)
            btnLogin?.setOnClickListener {
                val intent = Intent(this, Financial_education::class.java)
                startActivity(intent)
            }

            val btnlogout = findViewById<Button>(R.id.btnlogout)
            btnlogout?.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

            val usernameInput = findViewById<EditText>(R.id.editTextText)
            val passwordInput = findViewById<EditText>(R.id.editTextText3)
            val submitButton = findViewById<Button>(R.id.btnsignup)

            submitButton.setOnClickListener {
                val name = usernameInput.text.toString().trim()
                val password = passwordInput.text.toString().trim()

                when {
                    name.isEmpty() -> usernameInput.error = "Username cannot be empty"
                    password.isEmpty() -> passwordInput.error = "Password cannot be empty"
                    else -> {
                        val user = userDao.getUser(name, password)
                        if (user != null) {
                            val intent = Intent(this, CategoryActivity::class.java)
                            intent.putExtra("USERNAME", name)
                            startActivity(intent)
                            Log.d("LoginCheck", "Trying login with: $name / $password")
                            Log.d("LoginCheck", "User exists: ${user != null}")
                            finish()
                        } else {
                            passwordInput.error = "Invalid username or password"
                        }
                    }
                }

            }
        }
            }
        }

