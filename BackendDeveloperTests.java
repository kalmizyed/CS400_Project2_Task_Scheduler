// --== CS400 Project Two File Header ==--
// Name: Joseph Cai
// CSL Username: josephc
// Email: jbcai@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader: getOverdue is dependent on the current date and time, so its behavior is not consistent. 
// Thus, testGetOverdue is working right now but will not work later

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BackendDeveloperTests {
    /**
     * Tests basic add and remove functionality
     */
    @Test
    void testAddRemove(){
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        assertTrue(backend.addTask("03/28/2022-02:02", "Task2"));
        assertTrue(backend.addTask("03/28/2022-01:01", "Task1"));
        assertTrue(backend.addTask("03/28/2022-03:03", "Task3"));
        assertFalse(backend.addTask("fdsa", "Task3"));
        assertEquals(backend.tree.size(), 3);
        //Date does 1900+year, 0-indexed months
        ITask task1 = new TaskPlaceholder("Task1", new Date(122, 2,28,1,1));
        ITask task2 = new TaskPlaceholder("Task2", new Date(122, 2,28,2,2));
        ITask task3 = new TaskPlaceholder("Task3", new Date(122, 2,28,3,3));
        assertEquals(task1.compareTo(backend.removeTask(task1)),0);
        assertEquals(task2.compareTo(backend.removeTask(task2)),0);
        assertEquals(task3.compareTo(backend.removeTask(task3)),0);
        assertEquals(backend.removeTask(task1), null);
    }

    /**
     * Tests getAllTasks method
     */
    @Test
    void testGetAllTasks(){
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        backend.addTask("03/28/2022-02:02", "Task2");
        backend.addTask("03/28/2022-01:01", "Task1");
        backend.addTask("03/28/2022-03:03", "Task3");
        List<ITask> list = backend.getAllTasks();
        assertEquals(list.size(), 3);
    }

    /**
     * Tests getOverdue method
     * WARNING: getOverdue is based on the current time, so this is not stable
     */
    @Test
    void testGetOverdue(){
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        //THIS CHANGES BASED ON CURRENT DATE
        backend.addTask("03/26/2022-02:02", "Task1");
        backend.addTask("03/27/2022-01:01", "Task2");
        backend.addTask("03/28/2022-03:03", "Task3");
        backend.addTask("03/29/2022-02:02", "Task4");
        backend.addTask("03/30/2022-01:01", "Task5");
        backend.addTask("03/31/2022-03:03", "Task6");
        List<ITask> list = backend.getOverdue();
        assertEquals(list.size(), 3);
    }

    /**
     * Tests getBetweenDates method
     */
    @Test
    void testGetBetweenDates() throws ParseException{
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        backend.addTask("03/26/2022-02:02", "Task1");
        backend.addTask("03/27/2022-01:01", "Task2");
        backend.addTask("03/28/2022-03:03", "Task3");
        backend.addTask("03/29/2022-02:02", "Task4");
        backend.addTask("03/30/2022-01:01", "Task5");
        backend.addTask("03/31/2022-03:03", "Task6");
        List<ITask> list = backend.getBetweenDates("03/25/2022-01:01", "03/31/2022-11:11");
        assertEquals(list.size(), 6);
        list = backend.getBetweenDates("03/25/2022-01:01", "03/29/2022-11:11");
        assertEquals(list.size(), 4);
        list = backend.getBetweenDates("03/29/2022-01:01", "03/31/2022-11:11");
        assertEquals(list.size(), 3);
        list = backend.getBetweenDates("03/28/2022-01:01", "03/29/2022-11:11");
        assertEquals(list.size(), 2);
    }

    /**
     * Tests XML load and save functionality. Since that's mostly DataWrangler's job, I just test to see if the file exists
     */
    @Test
    void testLoadSave(){
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        backend.saveTree();
        backend.loadTree();
        assertEquals(backend.getAllTasks().size(), 4);
    }
}
