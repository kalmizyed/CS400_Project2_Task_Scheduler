import java.util.List;

public interface ITaskSchedulerBackend {
    /**
     * Creates a new Task and adds it to the RB Tree.
     * @param dateString String representing the date and time of the task
     * @param name the name of the task
     * @return true if task was successfully added, false otherwise
     */
    boolean addTask(String dateString, String name);

    /**
     * Removes the given Task object from the tree.
     * @param t the task to remove
     * @return the removed task
     */
    ITask removeTask(ITask t);

    /**
     * @return a list of all tasks in the tree
     */
    List<ITask> getAllTasks();

    /**
     * @return a list of all overdue tasks in the tree
     */
    List<ITask> getOverdue();

    /**
     * Loads the saved state of the tree from an XML file.
     * Makes use of the ITreeFileHandler class.
     */
    void loadTree();

    /**
     * Saves the current state of the tree to an XML file.
     * Makes use of the ITreeFileHandler class.
     */
    void saveTree();
}
