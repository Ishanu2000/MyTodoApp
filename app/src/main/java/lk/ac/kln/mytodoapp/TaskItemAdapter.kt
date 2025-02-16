package lk.ac.kln.mytodoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lk.ac.kln.mytodoapp.databinding.TaskItemCellBinding

/**
 * Adapter for the RecyclerView that displays a list of task items.
 *
 * @property taskItems The list of tasks to be displayed.
 * @property clickListener The listener for handling task item click events.
 */
class TaskItemAdapter(
    private val taskItems: List<TaskItem>,
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>() {

    /**
     * Called when the RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new TaskItemViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder that should be updated.
     * @param position The position of the item in the dataset.
     */
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    /**
     * Returns the total number of items in the dataset.
     *
     * @return The size of the taskItems list.
     */
    override fun getItemCount(): Int = taskItems.size
}
