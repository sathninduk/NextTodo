package com.example.nexttodo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.nexttodo.entities.Task

@Dao
interface TaskDao {
    @Insert(onConflict = REPLACE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): List<Task>
}