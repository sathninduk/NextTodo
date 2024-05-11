package com.example.nexttodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.nexttodo.database.TaskDatabase
import com.example.nexttodo.entities.Task
import com.example.nexttodo.repositories.TaskRepository
import com.example.nexttodo.viewmodels.TaskViewModel
import com.example.nexttodo.viewmodels.TaskViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * AddTaskActivity class handles adding new tasks.
 */
class AddTaskActivity : AppCompatActivity() {

    // UI components
    private lateinit var datePickerButton: Button
    private lateinit var backButton: Button
    private lateinit var saveTaskButton: Button
    private lateinit var titleInput: EditText
    private lateinit var bodyInput: EditText
    private lateinit var taskViewModel: TaskViewModel

    /**
     * Initializes the activity and sets up UI components and listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // Initializing views
        datePickerButton = findViewById(R.id.datePickerButton)
        backButton = findViewById(R.id.backButton)
        saveTaskButton = findViewById(R.id.saveTaskButton)
        titleInput = findViewById(R.id.titleInput)
        bodyInput = findViewById(R.id.bodyInput)

        // Create a date picker builder
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()

        // Build the date picker
        val datePicker = datePickerBuilder.build()

        // Setting click listener for back button
        backButton.setOnClickListener {
            backToMainActivity(this)
        }

        // Setting click listener for date picker button
        datePickerButton.setOnClickListener {
            // Show the date picker dialog when the button is clicked
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        // Setting positive button click listener for date picker dialog
        datePicker.addOnPositiveButtonClickListener { selection ->
            // Convert selected date to a human-readable date string and set it as the button text
            val dateString = DateFormat.getDateInstance().format(Date(selection))
            datePickerButton.text = dateString
        }

        // Initializing ViewModel
        val taskDao = TaskDatabase.getDatabase(this).taskDao()
        val repository = TaskRepository(taskDao)
        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        // Setting click listener for save task button
        saveTaskButton.setOnClickListener {
            val title = titleInput.text.toString()
            val notes = bodyInput.text.toString()
            val dateString = datePickerButton.text.toString()

            // Parsing and formatting date string
            val format = SimpleDateFormat("MMMM d, yyyy", Locale.US)
            var utilDate: Date? = null
            if (dateString.isNotBlank()) {
                try {
                    utilDate = format.parse(dateString)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }

            // Creating a task object with user inputs
            val sqlDate = java.sql.Date(utilDate?.time ?: 0)
            val task = Task(0, title, notes, 0, sqlDate)

            // Inserting task into ViewModel
            taskViewModel.insert(task)

            // Showing success message
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()

            // Navigating back to main activity
            backToMainActivity(this)
        }
    }

    /**
     * Navigates back to the main activity.
     * @param context The context from which the activity is navigated.
     */
    private fun backToMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}
