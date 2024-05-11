package com.example.nexttodo.repositories

import androidx.lifecycle.LiveData
import com.example.nexttodo.dao.TaskDao
import com.example.nexttodo.entities.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// TaskRepository class is a repository class that abstracts access to multiple data sources.
class TaskRepository(private val taskDao: TaskDao) {

    // Insert a task into the database
    suspend fun insert(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insert(task)
    }

    // Update a task in the database
    suspend fun update(task: Task) = withContext(Dispatchers.IO) {
        taskDao.update(task)
    }

    // Delete a task from the database
    suspend fun delete(task: Task) = withContext(Dispatchers.IO) {
        taskDao.delete(task)
    }

    // Get a task by ID
    fun getTaskById(id: Int): LiveData<Task> = taskDao.getTaskById(id)

    // Get all tasks
    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    // Get completed tasks
    fun getCompletedTasksCount(): LiveData<Int> = taskDao.getCompletedTasksCount()

    // Get uncompleted tasks
    fun getUncompletedTasksCount(): LiveData<Int> = taskDao.getUncompletedTasksCount()

    // Get today tasks
    fun getTodayTasksCount(): LiveData<Int> = taskDao.getTodayTasksCount()

    // Get overdue tasks
    fun getOverdueTasksCount(): LiveData<Int> = taskDao.getOverdueTasksCount()
}