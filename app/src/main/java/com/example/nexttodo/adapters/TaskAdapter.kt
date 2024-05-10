package com.example.nexttodo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nexttodo.entities.Task
import com.example.nexttodo.R
import com.example.nexttodo.viewmodels.TaskViewModel

class TaskAdapter(var tasks: List<Task>, private val taskViewModel: TaskViewModel) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.notification_title_9)
        val description: TextView = itemView.findViewById(R.id.notification_body_9)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.description.text = task.description

        holder.deleteButton.setOnClickListener {
            taskViewModel.delete(task)
        }
    }

    override fun getItemCount() = tasks.size
}