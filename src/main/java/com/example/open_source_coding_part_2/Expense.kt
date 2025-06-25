package com.example.open_source_coding_part_2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val category: String,
    val photoUri: String? = null
)
