package com.example.nexttodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
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
import java.text.ParseException
import java.text.SimpleDateFormat

class AddTaskActivity : AppCompatActivity() {

    private lateinit var datePickerButton: Button
    private lateinit var backButton: Button
    private lateinit var saveTaskButton: Button

    private lateinit var titleInput: EditText
    private lateinit var bodyInput: EditText

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        datePickerButton = findViewById(R.id.datePickerButton)
        backButton = findViewById(R.id.backButton)

        saveTaskButton = findViewById(R.id.saveTaskButton)
        titleInput = findViewById(R.id.titleInput)
        bodyInput = findViewById(R.id.bodyInput)


        // Create a date picker builder
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()

        // Build the date picker
        val datePicker = datePickerBuilder.build()

        backButton.setOnClickListener {
            backToMainActivity(this)
        }

        // Set the button click listener
        datePickerButton.setOnClickListener {
            // Show the date picker dialog when the button is clicked
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        // Set the date picker dialog positive button click listener
        datePicker.addOnPositiveButtonClickListener { selection ->
            // The selection parameter contains the selected date in milliseconds since epoch
            // Convert it to a human-readable date string and set it as the button text
            val dateString = DateFormat.getDateInstance().format(Date(selection))
            datePickerButton.text = dateString
        }

        val taskDao = TaskDatabase.getDatabase(this).taskDao()
        val repository = TaskRepository(taskDao)
        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        saveTaskButton.setOnClickListener {
            val title = titleInput.text.toString()
            val notes = bodyInput.text.toString()
            val dateString = datePickerButton.text.toString()

            val format = SimpleDateFormat("MMMM d, yyyy", Locale.US)
            var utilDate: Date? = null
            if (dateString.isNotBlank()) {
                try {
                    println("Parsing date string: $dateString")
                    utilDate = format.parse(dateString)
                    println("Parsed date: $utilDate")
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            } else {
                println("dateString is null or empty")
            }
            val sqlDate = java.sql.Date(utilDate?.time ?: 0)

            // create a task object
            val task = Task(0, title, notes, 0, sqlDate)

            taskViewModel.insert(task)

            // on success, give a toast message
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()

            // go back to the main activity
            backToMainActivity(this)

        }

    }

    private fun backToMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }


}