package com.example.nexttodo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexttodo.entities.Task
import com.example.nexttodo.repositories.TaskRepository
import kotlinx.coroutines.launch


class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun getAllTasks() = viewModelScope.launch {
        repository.getAllTasks()
    }
}