package lk.ac.kln.mytodoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/**
 * ViewModel for managing task items in the to-do list.
 * It maintains a list of tasks and provides methods to add, update, and mark tasks as completed.
 */
class TaskViewModel : ViewModel() {

    /**
     * LiveData holding a mutable list of task items.
     */
    var taskItems = MutableLiveData<MutableList<TaskItem>>()

    /**
     * Initializes the ViewModel with an empty task list.
     */
    init {
        taskItems.value = mutableListOf()
    }

    /**
     * Adds a new task to the list and updates LiveData.
     *
     * @param newTask The task item to be added.
     */
    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    /**
     * Updates an existing task's details.
     *
     * @param id The unique identifier of the task to update.
     * @param name The new name of the task.
     * @param desc The new description of the task.
     * @param dueTime The updated due time for the task.
     */
    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime = dueTime
        taskItems.postValue(list)
    }

    /**
     * Marks a task as completed by setting the completion date.
     *
     * @param taskItem The task item to be marked as completed.
     */
    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!

        if (task.completedDate == null) {
            task.completedDate = LocalDate.now()
        }

        taskItems.postValue(list)
    }
}
