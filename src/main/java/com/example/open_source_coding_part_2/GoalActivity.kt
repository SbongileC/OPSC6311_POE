package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class GoalActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal)
        
        val minGoalInput = findViewById<EditText>(R.id.minGoalInput)
        val maxGoalInput = findViewById<EditText>(R.id.maxGoalInput)
        val saveButton = findViewById<Button>(R.id.saveButton)


            saveButton.setOnClickListener {
                val minGoal = minGoalInput.text.toString().toDoubleOrNull()
                val maxGoal = maxGoalInput.text.toString().toDoubleOrNull()

                if (minGoal == null || maxGoal == null) {
                    Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show()
                } else if (minGoal > maxGoal) {
                    Toast.makeText(
                        this,
                        "Minimum goal cannot be greater than maximum goal.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "Goals saved!", Toast.LENGTH_SHORT).show()

                    // Save to preferences/database here
                }
            }
        }
    }
