package com.example.nexttodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import android.os.Bundle
import android.widget.Button

class AddTaskActivity : AppCompatActivity() {

    private lateinit var datePickerButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        datePickerButton = findViewById(R.id.datePickerButton)
        backButton = findViewById(R.id.backButton)



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


    }

    private fun backToMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }


}