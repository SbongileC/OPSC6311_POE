package com.example.open_source_coding_part_2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlin.collections.List

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM Expense")
    suspend fun getAllExpenses(): List<Expense>
}
