package lk.ac.kln.mytodoapp

/**
 * Interface to handle task item click events.
 */
interface TaskItemClickListener {

    /**
     * Called when a task item is selected for editing.
     *
     * @param taskItem The task item to be edited.
     */
    fun editTaskItem(taskItem: TaskItem)

    /**
     * Called when a task item is marked as completed.
     *
     * @param taskItem The task item to be marked as completed.
     */
    fun completeTaskItem(taskItem: TaskItem)
}
