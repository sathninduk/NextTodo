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

/**
 * MainActivity class represents the main screen of the application.
 */
class MainActivity : AppCompatActivity() {

    // UI components
    private lateinit var addTaskButton: Button
    private lateinit var tasksList: RecyclerView
    private lateinit var noTaskMessage: TextView
    private lateinit var todayTasks: TextView
    private lateinit var totalTasks: TextView
    private lateinit var overdueTasks: TextView
    private lateinit var completedTasks: TextView

    /**
     * Initializes the activity and sets up UI components.
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Finding views by their IDs
        addTaskButton = findViewById(R.id.addTaskBtn)
        todayTasks = findViewById(R.id.todayTasks)
        totalTasks = findViewById(R.id.totalTasks)
        overdueTasks = findViewById(R.id.overdueTasks)
        completedTasks = findViewById(R.id.completedTasks)
        tasksList = findViewById(R.id.TasksList)

        // Setting up RecyclerView layout manager
        tasksList.layoutManager = LinearLayoutManager(this)

        // Setting up ViewModel and Repository
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        val repository = TaskRepository(taskDao)
        val viewModelFactory = TaskViewModelFactory(repository)
        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        // Observing data changes for various task counts
        taskViewModel.getTodayTasksCount().observe(this, Observer { tasks -> // Observes the LiveData object for today's tasks count.
            todayTasks.text = tasks.toString()
        })

        taskViewModel.getAllTasks().observe(this, Observer { tasks -> // Observes the LiveData object for all tasks.
            totalTasks.text = tasks.size.toString()
        })

        taskViewModel.getOverdueTasksCount().observe(this, Observer { tasks -> // Observes the LiveData object for overdue tasks count.
            overdueTasks.text = tasks.toString()
        })

        taskViewModel.getCompletedTasksCount().observe(this, Observer { tasks -> // Observes the LiveData object for completed tasks count.
            completedTasks.text = tasks.toString()
        })

        // Hiding "No tasks" message initially
        noTaskMessage = findViewById(R.id.noTasks)
        noTaskMessage.visibility = View.GONE

        // Setting up adapter for task list
        val adapter = TaskAdapter(emptyList(), taskViewModel)
        tasksList.adapter = adapter

        // Observing task list changes
        taskViewModel.getAllTasks().observe(this, Observer { tasks -> // Observes the LiveData object for all tasks.
            adapter.tasks = tasks
            adapter.notifyDataSetChanged()

            // Showing or hiding message based on tasks availability
            if (tasks.isEmpty()) {
                noTaskMessage.visibility = View.VISIBLE
            } else {
                noTaskMessage.visibility = View.GONE
            }
        })

        // Attaching adapter to recycler view
        adapter.attachToRecyclerView(tasksList)

        // Setting up click listener for "Add Task" button
        addTaskButton.setOnClickListener {
            startAddTaskActivity(this)
        }
    }

    /**
     * Starts the AddTaskActivity.
     * @param context The context from which the activity is started.
     */
    private fun startAddTaskActivity(context: Context) {
        val intent = Intent(context, AddTaskActivity::class.java)
        context.startActivity(intent)
    }
}
