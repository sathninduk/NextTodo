package com.example.nexttodo.repositories

import com.example.nexttodo.dao.TaskDao
import com.example.nexttodo.entities.Task


class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }
}