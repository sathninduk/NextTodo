package com.example.nexttodo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexttodo.entities.Task
import com.example.nexttodo.repositories.TaskRepository
import kotlinx.coroutines.launch


class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    fun insert(task: Task) = viewModelScope.launch {
        try {
            repository.insert(task)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        try {
            repository.delete(task)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTaskById(id: Int): LiveData<Task> {
        try {
            return repository.getTaskById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getAllTasks(): LiveData<List<Task>> {
        try {
            return repository.getAllTasks()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getCompletedTasksCount(): LiveData<Int> {
        try {
            return repository.getCompletedTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getUncompletedTasksCount(): LiveData<Int> {
        try {
            return repository.getUncompletedTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getTodayTasksCount(): LiveData<Int> {
        try {
            return repository.getTodayTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getOverdueTasksCount(): LiveData<Int> {
        try {
            return repository.getOverdueTasksCount()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}