package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Financial_education : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial_education)

        val incomeInput = findViewById<EditText>(R.id.incomeInput)
        val resultText = findViewById<TextView>(R.id.resultText)
        val calculateBtn = findViewById<Button>(R.id.calculateBtn)

        val Btnnex = findViewById<Button>(R.id.Btnnex)
        Btnnex.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }

// ✅ This should be on its own
        calculateBtn.setOnClickListener {
            val income = incomeInput.text.toString().toDoubleOrNull()

            if (income == null || income <= 0) {
                resultText.text = "Please enter a valid income."
                return@setOnClickListener
            }

            val needs = income * 0.5
            val wants = income * 0.3
            val savings = income * 0.2

            resultText.text = """
        Budget Breakdown:
        ✅ Needs (50%): R${String.format("%.2f", needs)}
        🎉 Wants (30%): R${String.format("%.2f", wants)}
        💾 Savings (20%): R${String.format("%.2f", savings)}
    """.trimIndent()
        }
    }
}

