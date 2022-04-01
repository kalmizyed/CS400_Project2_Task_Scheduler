import java.util.List;

public interface ITaskSchedulerFrontend {
    /**
     * Starts the text UI.
     */
    void runCommandLoop();

    /**
     * Prints a list of commands and allows the user to choose one.
     */
    void showCommandMenu();

    /**
     * Displays all tasks.
     * Makes use of displayTaskList().
     */
    void displayAllTasks();

    /**
     * Displays overdue tasks.
     * Makes use of displayTaskList().
     */
    void displayOverdueTasks();

    /**
     * Displays a given list of tasks.
     * @param tasks list of tasks to be displayed
     */
    void displayTaskList(List<ITask> tasks);

    /**
     * Adds a task based on user input of the due date and task name.
     */
    void addTask();

    /**
     * Allows the user to enter dates then displays all the tasks between the given dates
     */
    void displayTasksBetweenDates();

    /**
     * Displays all tasks and allows the user to
     * enter the number of the one to complete,
     * then removes it.
     */
    void completeTask();
}

