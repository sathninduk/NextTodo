package com.example.nexttodo.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nexttodo.EditTaskActivity
import com.example.nexttodo.entities.Task
import com.example.nexttodo.R
import com.example.nexttodo.viewmodels.TaskViewModel

class TaskAdapter(var tasks: List<Task>, private val taskViewModel: TaskViewModel) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.notification_title_9)
        val description: TextView = itemView.findViewById(R.id.notification_body_9)
        val deadline: TextView = itemView.findViewById(R.id.notification_deadline_9)
        val notification: View = itemView.findViewById(R.id.notification_9)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.description.text = task.description
        holder.deadline.text = task.deadline.toString()

        holder.notification.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditTaskActivity::class.java)
            intent.putExtra("TASK_ID", task.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = tasks.size

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // We are not implementing move functionality here
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =
                    viewHolder.adapterPosition // Get the position of the item to be deleted
                if (direction == ItemTouchHelper.LEFT) {
                    taskViewModel.delete(tasks[position]) // Delete the item from your list

                    // toasts a message to the user
                    val context = viewHolder.itemView.context
                    Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()

                } else if (direction == ItemTouchHelper.RIGHT) {
                    val task = tasks[position].copy(completed = 1)
                    taskViewModel.update(task) // Set the task as completed

                    // toasts a message to the user
                    val context = viewHolder.itemView.context
                    Toast.makeText(context, "Task completed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}