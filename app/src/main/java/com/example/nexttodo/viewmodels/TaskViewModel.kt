package com.example.nexttodo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexttodo.entities.Task
import com.example.nexttodo.repositories.TaskRepository
import kotlinx.coroutines.launch


// TaskViewModel class is a ViewModel class that provides data to the UI and survives configuration changes.
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Insert a task into the database
    fun insert(task: Task) = viewModelScope.launch {
        try {
            repository.insert(task)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Update a task in the database
    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    // Delete a task from the database
    fun delete(task: Task) = viewModelScope.launch {
        try {
            repository.delete(task)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Get a task by ID
    fun getTaskById(id: Int): LiveData<Task> {
        try {
            return repository.getTaskById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    // Get all tasks
    fun getAllTasks(): LiveData<List<Task>> {
        try {
            return repository.getAllTasks()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    // Get completed tasks
    fun getCompletedTasksCount(): LiveData<Int> {
        try {
            return repository.getCompletedTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    // Get uncompleted tasks
    fun getUncompletedTasksCount(): LiveData<Int> {
        try {
            return repository.getUncompletedTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    // Get today's tasks
    fun getTodayTasksCount(): LiveData<Int> {
        try {
            return repository.getTodayTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    // Get overdue tasks
    fun getOverdueTasksCount(): LiveData<Int> {
        try {
            return repository.getOverdueTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}