package com.example.nexttodo.dao

import androidx.room.*
import com.example.nexttodo.entities.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): List<Task>
}