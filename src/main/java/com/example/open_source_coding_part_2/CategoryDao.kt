package com.example.open_source_coding_part_2

// CategoryDao.kt
import androidx.room.*
import kotlin.collections.List

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>
}
