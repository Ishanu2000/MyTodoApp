package lk.ac.kln.mytodoapp

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import lk.ac.kln.mytodoapp.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

/**
 * ViewHolder for displaying a single task item in a RecyclerView.
 *
 * @param context The application context.
 * @param binding The view binding for the task item cell layout.
 * @param clickListener Listener for handling task item click events.
 */
class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    // Formatter for displaying time in HH:mm format
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    /**
     * Binds a task item to the ViewHolder.
     *
     * @param taskItem The task item to be displayed.
     */
    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name

        // Strike through text if the task is completed
        if (taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        // Set task completion button icon and color
        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        // Handle task completion click event
        binding.completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        // Handle task edit click event
        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        // Display the due time if available
        binding.dueTime.text = taskItem.dueTime?.let { timeFormat.format(it) } ?: ""
    }
}
