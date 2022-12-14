// --== CS400 Project Two File Header ==--
// Name: Joseph Cai
// CSL Username: josephc
// Email: jbcai@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader: 

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.zip.DataFormatException;

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
     * @throws ParseException if the input strings are not correctly formatted
     */
    List<ITask> getBetweenDates(String minDateString, String maxDateString) throws ParseException;

    /**
     * Loads the saved state of the tree from an XML file.
     * Makes use of the ITreeFileHandler class.
     * @throws DataFormatException
     * @throws FileNotFoundException
     */
    void loadTree() throws FileNotFoundException, DataFormatException;

    /**
     * Saves the current state of the tree to an XML file.
     * Makes use of the ITreeFileHandler class.
     */
    void saveTree();
}
