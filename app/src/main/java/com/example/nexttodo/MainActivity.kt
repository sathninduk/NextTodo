package com.example.nexttodo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexttodo.adapters.TaskAdapter
import com.example.nexttodo.database.TaskDatabase
import com.example.nexttodo.entities.Task
import com.example.nexttodo.repositories.TaskRepository
import com.example.nexttodo.viewmodels.TaskViewModel
import com.example.nexttodo.viewmodels.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var addTaskButton: Button
    private lateinit var tasksList: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTaskButton = findViewById(R.id.addTaskBtn)

        addTaskButton.setOnClickListener {
            startAddTaskActivity(this)
        }

        tasksList = findViewById(R.id.TasksList)
        tasksList.layoutManager = LinearLayoutManager(this)

        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        val repository = TaskRepository(taskDao)

        val viewModelFactory = TaskViewModelFactory(repository)
        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        val adapter = TaskAdapter(emptyList())
        tasksList.adapter = adapter

        taskViewModel.getAllTasks().observe(this, Observer { tasks ->
            adapter.tasks = tasks
            adapter.notifyDataSetChanged()
        })


    }

    private fun startAddTaskActivity(context: Context) {
        val intent = Intent(context, AddTaskActivity::class.java)
        context.startActivity(intent)
    }
}