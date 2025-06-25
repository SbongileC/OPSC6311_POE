package com.example.open_source_coding_part_2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class ExpenseActivity : AppCompatActivity() {

    private lateinit var dateInput: EditText
    private lateinit var startTimeInput: EditText
    private lateinit var endTimeInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var photoPreview: ImageView
    private lateinit var selectPhotoButton: Button
    private lateinit var saveExpenseButton: Button

    // Holds the URI of the attached photo (if any)
    private var photoUri: Uri? = null

    // Launcher for picking an image from gallery
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photoUri = it
            photoPreview.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense)

        val btncont = findViewById<Button>(R.id.btncont)
        btncont?.setOnClickListener {
            val intent = Intent(this, Chart::class.java)
            startActivity(intent)
        }

        // 1) findViewById…
        dateInput          = findViewById(R.id.dateInput)
        startTimeInput     = findViewById(R.id.startTimeInput)
        endTimeInput       = findViewById(R.id.endTimeInput)
        descriptionInput   = findViewById(R.id.descriptionInput)
        categorySpinner    = findViewById(R.id.categorySpinner)
        photoPreview       = findViewById(R.id.photoPreview)
        selectPhotoButton  = findViewById(R.id.selectPhotoButton)
        saveExpenseButton  = findViewById(R.id.saveExpenseButton)

        // 2) Date picker
        dateInput.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    cal.set(year, month, dayOfMonth)
                    dateInput.setText(fmt.format(cal.time))
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 3) Start / End time pickers
        startTimeInput.setOnClickListener { showTimePicker(startTimeInput) }
        endTimeInput.setOnClickListener   { showTimePicker(endTimeInput) }

        // 4) Spinner: load categories (in a real app, fetch from DB or pass via Intent)
        val dummyCategories = listOf("Food", "Transport", "Utilities", "Entertainment")
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dummyCategories
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = it
        }

        // 5) Photo picker button
        selectPhotoButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // 6) Save button
        saveExpenseButton.setOnClickListener {
            val date        = dateInput.text.toString()
            val startTime   = startTimeInput.text.toString()
            val endTime     = endTimeInput.text.toString()
            val desc        = descriptionInput.text.toString()
            val category    = categorySpinner.selectedItem as String
            val hasPhoto    = if (photoUri != null) "Yes" else "No"
            val db = AppDatabase.getDatabase(this)

            if (date.isBlank() || startTime.isBlank() || endTime.isBlank() || desc.isBlank()) {
                Toast.makeText(this,
                    "Please fill in date, start, end and description",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val expense = Expense(
                date = date,
                startTime = startTime,
                endTime = endTime,
                description = desc,
                category = category,
                photoUri = photoUri?.toString()
            )

            lifecycleScope.launch {
                db.expenseDao().insertExpense(expense)
                Toast.makeText(this@ExpenseActivity, "Expense saved successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@ExpenseActivity, Chart::class.java)
                startActivity(intent)
            }
        }
    }

    /** Helper to show a TimePickerDialog and put result into the given EditText */
    private fun showTimePicker(target: EditText) {
        val cal = Calendar.getInstance()
        TimePickerDialog(this,
            { _, hour, minute ->
                // format as HH:mm
                val formatted = String.format(Locale.US, "%02d:%02d", hour, minute)
                target.setText(formatted)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }
}