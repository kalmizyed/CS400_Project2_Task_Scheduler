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
     * Uses the getItemsBetween of the IExtendedSortedCollection by creating Task
     * objects with Dates made from the given date strings
     * @param minDateString earliest date string for tasks in the list to return
     * @param maxDateString latest date string for tasks in the list to return
     * @return a list of all the tasks between the dates
     */
    List<ITask> getBetweenDates(String minDateString, String maxDateString);

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
