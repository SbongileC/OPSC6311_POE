package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryInput: EditText
    private lateinit var addCategoryButton: Button
    private lateinit var categoryListView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val categoryList = mutableListOf<String>()
    private lateinit var db: AppDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category)

        db = AppDatabase.getDatabase(this)

        // Initialize views
        categoryInput = findViewById(R.id.categoryInput)
        addCategoryButton = findViewById(R.id.addCategoryButton)
        categoryListView = findViewById(R.id.categoryListView)

        // Setup adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryList)
        categoryListView.adapter = adapter

        // Load existing categories
        lifecycleScope.launch {
            val savedCategories = db.categoryDao().getAllCategories()
            categoryList.addAll(savedCategories.map { it.name })
            adapter.notifyDataSetChanged()
        }

        // Add button click logic
        addCategoryButton.setOnClickListener {
            val inputText = categoryInput.text.toString().trim()
            if (inputText.isNotEmpty()) {
                val newCategory = Category(name = inputText)
                lifecycleScope.launch {
                    db.categoryDao().insertCategory(newCategory)
                    categoryList.add(inputText)
                    adapter.notifyDataSetChanged()
                    categoryInput.text.clear()
                }
            } else {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            }
        }

        val btnNext = findViewById<Button>(R.id.btnlogin)
        btnNext?.setOnClickListener {
            val intent = Intent(this, ExpenseActivity::class.java)
            startActivity(intent)
        }

        // Optional: Toast on clicking a list item
        categoryListView.setOnItemClickListener { _, _, position, _ ->
            val clickedCategory = categoryList[position]
            Toast.makeText(this, "Clicked: $clickedCategory", Toast.LENGTH_SHORT).show()
        }
    }
}
