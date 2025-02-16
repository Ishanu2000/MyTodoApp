package lk.ac.kln.mytodoapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import lk.ac.kln.mytodoapp.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

/**
 * A bottom sheet dialog fragment for creating or editing a task.
 *
 * @param taskItem The task item to be edited. If null, a new task is created.
 */
class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    /** View binding instance for accessing UI components. */
    private lateinit var binding: FragmentNewTaskSheetBinding

    /** ViewModel for managing task data and logic. */
    private lateinit var taskViewModel: TaskViewModel

    /** Stores the selected due time for the task. */
    private var dueTime: LocalTime? = null

    /**
     * Called when the view is created. Initializes UI components and loads task data if editing.
     *
     * @param view The fragment's root view.
     * @param savedInstanceState The saved state from a previous instance, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null) {
            // Populate fields if editing an existing task
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime != null) {
                dueTime = taskItem!!.dueTime!!
                updateTimeButtonText()
            }
        } else {
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        binding.saveButton.setOnClickListener {
            saveAction()
        }

        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
    }

    /**
     * Opens a time picker dialog to set the due time for the task.
     */
    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()

        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }

        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    /**
     * Updates the text on the time picker button with the selected time.
     */
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    /**
     * Called to create the fragment's view.
     *
     * @param inflater The LayoutInflater object used to inflate views.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The saved state from a previous instance, if any.
     * @return The root view of the fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Saves or updates the task in the ViewModel and dismisses the dialog.
     */
    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()

        if (taskItem == null) {
            // Create a new task
            val newTask = TaskItem(name, desc, dueTime, null)
            taskViewModel.addTaskItem(newTask)
        } else {
            // Update existing task
            taskViewModel.updateTaskItem(taskItem!!.id, name, desc, dueTime)
        }

        // Clear input fields and close the dialog
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }
}
