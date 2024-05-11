package com.example.nexttodo.repositories

import androidx.lifecycle.LiveData
import com.example.nexttodo.dao.TaskDao
import com.example.nexttodo.entities.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) = withContext(Dispatchers.IO) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) = withContext(Dispatchers.IO) {
        taskDao.delete(task)
    }


    fun getTaskById(id: Int): LiveData<Task> = taskDao.getTaskById(id)

    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    fun getCompletedTasksCount(): LiveData<Int> = taskDao.getCompletedTasksCount()

    fun getUncompletedTasksCount(): LiveData<Int> = taskDao.getUncompletedTasksCount()

    fun getTodayTasksCount(): LiveData<Int> = taskDao.getTodayTasksCount()

    fun getOverdueTasksCount(): LiveData<Int> = taskDao.getOverdueTasksCount()
}