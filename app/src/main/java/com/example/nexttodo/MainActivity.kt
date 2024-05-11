package com.example.nexttodo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexttodo.adapters.TaskAdapter
import com.example.nexttodo.database.TaskDatabase
import com.example.nexttodo.repositories.TaskRepository
import com.example.nexttodo.viewmodels.TaskViewModel
import com.example.nexttodo.viewmodels.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var addTaskButton: Button
    private lateinit var tasksList: RecyclerView
    private lateinit var noTaskMessage: TextView

    private lateinit var todayTasks: TextView
    private lateinit var totalTasks: TextView
    private lateinit var overdueTasks: TextView
    private lateinit var completedTasks: TextView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTaskButton = findViewById(R.id.addTaskBtn)

        addTaskButton.setOnClickListener {
            startAddTaskActivity(this)
        }

        todayTasks = findViewById(R.id.todayTasks)
        totalTasks = findViewById(R.id.totalTasks)
        overdueTasks = findViewById(R.id.overdueTasks)
        completedTasks = findViewById(R.id.completedTasks)

        tasksList = findViewById(R.id.TasksList)
        tasksList.layoutManager = LinearLayoutManager(this)

        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        val repository = TaskRepository(taskDao)

        val viewModelFactory = TaskViewModelFactory(repository)
        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        taskViewModel.getTodayTasksCount().observe(this, Observer { tasks ->
            todayTasks.text = tasks.toString()
        })

        taskViewModel.getAllTasks().observe(this, Observer { tasks ->
            totalTasks.text = tasks.size.toString()
        })

        taskViewModel.getOverdueTasksCount().observe(this, Observer { tasks ->
            overdueTasks.text = tasks.toString()
        })

        taskViewModel.getCompletedTasksCount().observe(this, Observer { tasks ->
            completedTasks.text = tasks.toString()
        })

        noTaskMessage = findViewById(R.id.noTasks)
        noTaskMessage.visibility = View.GONE

        val adapter = TaskAdapter(emptyList(), taskViewModel)
        tasksList.adapter = adapter

        taskViewModel.getAllTasks().observe(this, Observer { tasks ->

            adapter.tasks = tasks
            adapter.notifyDataSetChanged()

            if (tasks.isEmpty()) {
                noTaskMessage.visibility = View.VISIBLE
            } else {
                noTaskMessage.visibility = View.GONE
            }

        })


    }

    private fun startAddTaskActivity(context: Context) {
        val intent = Intent(context, AddTaskActivity::class.java)
        context.startActivity(intent)
    }
}