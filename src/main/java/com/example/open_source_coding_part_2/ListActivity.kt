package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.util.*

class ListActivity : AppCompatActivity() {
    private lateinit var editDate: EditText
    private lateinit var editSalary: EditText
    private lateinit var editCategory: EditText
    private lateinit var editAmount: EditText
    private lateinit var editDescription: EditText
    private lateinit var imagePreview: ImageView
    private lateinit var btnAttachPhoto: Button
    private lateinit var btnSave: Button
    private var selectedDate = Calendar.getInstance()
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageBitmap: Bitmap? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

                editDate = findViewById(R.id.editDate)
                editSalary = findViewById(R.id.editSalary)
                editCategory = findViewById(R.id.editCategory)
                editAmount = findViewById(R.id.editAmount)
                editDescription = findViewById(R.id.editDescription)
                imagePreview = findViewById(R.id.imagePreview)
                btnAttachPhoto = findViewById(R.id.btnAttachPhoto)
                btnSave = findViewById(R.id.btnSave)

                editDate.setOnClickListener {
                    showDatePicker()
                }

                btnAttachPhoto.setOnClickListener {
                    dispatchTakePictureIntent()
                }

                btnSave.setOnClickListener {
                    saveExpense()
                }
            }

            private fun showDatePicker() {
                val year = selectedDate.get(Calendar.YEAR)
                val month = selectedDate.get(Calendar.MONTH)
                val day = selectedDate.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                    selectedDate.set(year, month, day)
                    editDate.setText("${year}-${month + 1}-${day}")
                }, year, month, day)

                datePickerDialog.show()
            }

            private fun dispatchTakePictureIntent() {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    this.imageBitmap = imageBitmap
                    imagePreview.setImageBitmap(imageBitmap)
                    imagePreview.visibility = View.VISIBLE
                }
            }

            private fun saveExpense() {
                val date = editDate.text.toString()
                val salaryStr = editSalary.text.toString()
                val category = editCategory.text.toString()
                val amountStr = editAmount.text.toString()
                val description = editDescription.text.toString()

                if (salaryStr.isEmpty()) {
                    editSalary.error = "Please enter your salary"
                    return
                }

                if (amountStr.isEmpty()) {
                    editAmount.error = "Please enter the expense amount"
                    return
                }

                val salary = salaryStr.toDoubleOrNull()
                val amount = amountStr.toDoubleOrNull()

                if (salary == null || salary <= 0) {
                    editSalary.error = "Enter a valid salary"
                    return
                }

                if (amount == null || amount <= 0) {
                    editAmount.error = "Enter a valid amount"
                    return
                }

                // Check if spending exceeds 40% of salary
                val maxAllowed = 0.4 * salary
                if (amount > maxAllowed) {
                    Toast.makeText(
                        this,
                        "Warning: You are overspending! Expense exceeds 40% of your salary.",
                        Toast.LENGTH_LONG
                    ).show()
                    // Optionally, you can return here to prevent saving
                    return
                }

                // Save expense logic here (e.g., database insert)

                Toast.makeText(
                    this,
                    "Expense saved: $date, $category, $amount, $description",
                    Toast.LENGTH_SHORT
                ).show()
            }
}