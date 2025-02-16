package lk.ac.kln.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import lk.ac.kln.mytodoapp.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Main activity of the To-Do app.
 * Handles UI interactions and integrates with the ViewModel.
 */
class MainActivity : AppCompatActivity(), TaskItemClickListener {

    /** View binding instance for accessing UI components. */
    private lateinit var binding: ActivityMainBinding

    /** ViewModel for managing task data and logic. */
    private lateinit var taskViewModel: TaskViewModel

    /**
     * Called when the activity is first created.
     * Initializes UI components, ViewModel, and sets up event listeners.
     *
     * @param savedInstanceState Saved state from a previous instance, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.newTaskButton.setOnClickListener {
            // Opens the NewTaskSheet dialog for adding a new task
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()
    }

    /**
     * Sets up the RecyclerView to display the list of tasks.
     * Observes the ViewModel for task updates.
     */
    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    /**
     * Opens the task editing dialog when a task item is clicked.
     *
     * @param taskItem The task item to be edited.
     */
    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    /**
     * Marks a task as completed when the user interacts with it.
     *
     * @param taskItem The task item to be marked as completed.
     */
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}
