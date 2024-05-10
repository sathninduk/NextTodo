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
import java.util.Date
import kotlin.properties.Delegates

class EditTaskActivity : AppCompatActivity() {

    private lateinit var datePickerButton: Button
    private lateinit var backButton: Button
    private lateinit var saveTaskButton: Button

    private lateinit var titleInput: EditText
    private lateinit var bodyInput: EditText

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val taskId = intent.getIntExtra("TASK_ID", -1)
//        val task = taskViewModel.getTaskById(taskId)

        datePickerButton = findViewById(R.id.editdatePickerButton)
        backButton = findViewById(R.id.editBackButton)

        saveTaskButton = findViewById(R.id.editSaveTaskButton)
        titleInput = findViewById(R.id.edittitleInput)
        bodyInput = findViewById(R.id.editbodyInput)


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
            val dateString = DateFormat.getDateInstance().format(Date(selection))
            datePickerButton.text = dateString
        }

        val taskDao = TaskDatabase.getDatabase(this).taskDao()
        val repository = TaskRepository(taskDao)
        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        taskViewModel.getTaskById(taskId).observe(this) { task ->
            titleInput.setText(task.title)
            bodyInput.setText(task.description)
            datePickerButton.text = task.deadline
        }

        saveTaskButton.setOnClickListener {
            val title = titleInput.text.toString()
            val notes = bodyInput.text.toString()
            val date = datePickerButton.text.toString()

            val task = Task(taskId, title, notes, 1, date)

            taskViewModel.update(task)

            // on success, give a toast message
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()

            // go back to the main activity
            backToMainActivity(this)

        }

    }

    private fun backToMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

}