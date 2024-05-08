package com.example.nexttodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var addTaskButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTaskButton = findViewById(R.id.addTaskBtn)

        addTaskButton.setOnClickListener {
            startAddTaskActivity(this)
        }

    }

    private fun startAddTaskActivity(context: Context) {
        val intent = Intent(context, AddTaskActivity::class.java)
        context.startActivity(intent)
    }
}