import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

// --== CS400 Project Two File Header ==--
// Name: Joseph Cai
// CSL Username: josephc
// Email: jbcai@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader: getOverdue is dependent on the current date and time, so its behavior is not consistent
public class TaskSchedulerBackend implements ITaskSchedulerBackend{
    protected IExtendedSortedCollection<ITask> tree;
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-HH:mm");
    /**
     * Constructor
     */
    public TaskSchedulerBackend(){
        tree = new RedBlackTree<ITask>();
    }
    /**
     * Creates a new Task and adds it to the RB Tree.
     * @param dateString String representing the date and time of the task
     * @param name the name of the task
     * @return true if task was successfully added, false otherwise
     */
    public boolean addTask(String dateString, String name){
        try{
            ITask task = new Task(name, dateFormat.parse(dateString));
            tree.insert(task);
        }
        catch(ParseException e){
            return false;
        }
        return true;
    }

    /**
     * Removes the given Task object from the tree.
     * @param t the task to remove
     * @return the removed task
     */
    public ITask removeTask(ITask t){
        return tree.remove(t);
    }

    /**
     * @return a list of all tasks in the tree
     */
    public List<ITask> getAllTasks(){
        List<ITask> list = new ArrayList<ITask>();
        for(ITask task: tree){
            list.add(task);
        }
        return list;
    }

    /**
     * @return a list of all overdue tasks in the tree
     */
    public List<ITask> getOverdue(){
        Date currentDate = Calendar.getInstance().getTime();
        ITask tempTask = new Task("", currentDate);
        ITask firstTask = tree.iterator().next();
        return tree.getItemsBetween(firstTask, tempTask);
    }

    /**
     * Uses the getItemsBetween of the IExtendedSortedCollection by creating Task
     * objects with Dates made from the given date strings
     * @param minDateString earliest date string for tasks in the list to return
     * @param maxDateString latest date string for tasks in the list to return
     * @return a list of all the tasks between the dates
     * @throws ParseException if the input strings are not correctly formatted
     */
    public List<ITask> getBetweenDates(String minDateString, String maxDateString) throws ParseException{
        Date minDate = dateFormat.parse(minDateString);
        Date maxDate = dateFormat.parse(maxDateString);
        ITask minTask = new Task("", minDate);
        ITask maxTask = new Task("", maxDate);
        return tree.getItemsBetween(minTask, maxTask);
    }

    /**
     * Loads the saved state of the tree from an XML file.
     * Makes use of the ITreeFileHandler class.
     * @throws DataFormatException
     * @throws FileNotFoundException
     */
    public void loadTree() throws FileNotFoundException, DataFormatException{
        Path workingDirectory = FileSystems.getDefault().getPath("tasks.xml");
        File XMLFile = workingDirectory.toFile();
        ITreeFileHandler fileHandler= new TreeFileHandler();
        tree = fileHandler.getTreeFromFile(XMLFile);
    }

    /**
     * Saves the current state of the tree to an XML file.
     * Makes use of the ITreeFileHandler class.
     * @throws FileNotFoundException
     */
    public void saveTree(){
        Path workingDirectory = FileSystems.getDefault().getPath("tasks.xml");
        File XMLFile = workingDirectory.toFile();
        ITreeFileHandler fileHandler= new TreeFileHandler();
        if(!fileHandler.writeTreeToFile(XMLFile, tree)) throw new IllegalArgumentException();
    }
}