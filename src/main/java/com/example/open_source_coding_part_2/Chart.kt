package com.example.open_source_coding_part_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class Chart : AppCompatActivity() {
    private lateinit var pieChart: PieChart
    private lateinit var badge: ImageView
    private var minBudget = 0
    private var maxBudget = 0
    private var totalExpense = 0f
    private val expenseEntries = ArrayList<PieEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        val minInput = findViewById<EditText>(R.id.minBudgetInput)
        val maxInput = findViewById<EditText>(R.id.maxBudgetInput)
        val setBudgetBtn = findViewById<Button>(R.id.setBudgetBtn)
        val expenseInput = findViewById<EditText>(R.id.expenseInput)
        val addExpenseBtn = findViewById<Button>(R.id.addExpenseBtn)
        pieChart = findViewById(R.id.pieChart)
        badge = findViewById(R.id.congratsBadge)

        setBudgetBtn.setOnClickListener {
            minBudget = minInput.text.toString().toInt()
            maxBudget = maxInput.text.toString().toInt()
            Toast.makeText(this, "Budget Set!", Toast.LENGTH_SHORT).show()
        }

        val btnnex = findViewById<Button>(R.id.btnnex)
        btnnex?.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        addExpenseBtn.setOnClickListener {
            val expense = expenseInput.text.toString().toFloat()
            totalExpense += expense
            expenseEntries.add(PieEntry(expense, "Item ${expenseEntries.size + 1}"))
            updateChart()

            if (totalExpense >= maxBudget) {
                badge.visibility = View.VISIBLE
                Toast.makeText(this, "Congratulations! Max Budget Reached!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateChart() {
        val dataSet = PieDataSet(expenseEntries, "Expenses")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()
    }

}


