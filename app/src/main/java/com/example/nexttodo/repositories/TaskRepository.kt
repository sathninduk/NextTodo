package com.example.nexttodo.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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
}