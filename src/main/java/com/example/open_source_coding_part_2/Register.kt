package com.example.open_source_coding_part_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        fun add_user(view: View) {
            val btnSignUP = findViewById<Button>(R.id.reg)
            btnSignUP?.setOnClickListener {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
            //collecting the user's info
            val name: EditText = findViewById(R.id.name)
            val username: EditText = findViewById(R.id.username)
            val password: EditText = findViewById(R.id.password)

            //creating an instance for the firestore database
            val database = FirebaseFirestore.getInstance()

            //bind user info to store
            val new_user = hashMapOf(
                "name" to name.text.toString(),
                "username" to username.text.toString(),
                "password" to password.text.toString()
            )

// add info to the collection called users
            database.collection("users").add(new_user).addOnSuccessListener { documentReference ->
                Toast.makeText(this, "new User addaed", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { expection ->
                Toast.makeText(this, "Error: " + expection, Toast.LENGTH_SHORT).show()
            }
        }
    }
}