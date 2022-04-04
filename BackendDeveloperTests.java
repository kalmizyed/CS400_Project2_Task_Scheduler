// --== CS400 Project Two File Header ==--
// Name: Joseph Cai
// CSL Username: josephc
// Email: jbcai@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader: getOverdue is dependent on the current date and time, so its behavior is not consistent. 
// Thus, testGetOverdue is working right now but will not work later

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

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
     * Tests XML save functionality. This just tests to see if the file exists
     */
    @Test
    void testSaveTree(){
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        backend.addTask("03/03/2022-12:12", "Test");
        backend.saveTree();
        File xmlFile = new File("tasks.xml");
        assertTrue(xmlFile.exists());
    }

    /******************************* 
        START OF NEW TESTS
    ****************************/
    /*
     * Tests XML load and save functionality, with only one task
     */
    @Test
    void testLoadAndSaveTree() throws FileNotFoundException, DataFormatException{
        //Original backend
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        backend.addTask("03/03/2022-12:12", "Test");
        backend.saveTree(); //Save to file
        File xmlFile = new File("tasks.xml");

        //New backend
        TaskSchedulerBackend backend2 = new TaskSchedulerBackend();
        backend2.loadTree(); //Load from file
        ITask task = backend2.getAllTasks().get(0);
        assertEquals(task.getName(), "Test"); //Task with same name
        assertEquals(task.getDate().toString(), "Thu Mar 03 12:12:00 CST 2022"); //Same date
    }
    
    /**
     * Tests XML load and save functionality, with many tasks
     */
    @Test
    void testLoadAndSaveTree2() throws FileNotFoundException, DataFormatException{
        //Original backend
        TaskSchedulerBackend backend = new TaskSchedulerBackend();
        //Add tasks
        backend.addTask("03/05/2022-12:12", "Test5");
        backend.addTask("03/01/2022-12:12", "Test1");
        backend.addTask("03/04/2022-12:12", "Test4");
        backend.addTask("03/03/2022-12:12", "Test3");
        backend.addTask("03/02/2022-12:12", "Test2");
        backend.saveTree(); //Save to file
        File xmlFile = new File("tasks.xml");

        //New backend
        TaskSchedulerBackend backend2 = new TaskSchedulerBackend();
        backend2.loadTree(); //Load from file
        //Get all tasks
        ITask task1 = backend2.getAllTasks().get(0);
        ITask task2 = backend2.getAllTasks().get(1);
        ITask task3 = backend2.getAllTasks().get(2);
        ITask task4 = backend2.getAllTasks().get(3);
        ITask task5 = backend2.getAllTasks().get(4);
        //Check same name and same date
        assertEquals(task1.getName(), "Test1");
        assertEquals(task1.getDate().toString(), "Tue Mar 01 12:12:00 CST 2022");
        assertEquals(task2.getName(), "Test2");
        assertEquals(task2.getDate().toString(), "Wed Mar 02 12:12:00 CST 2022");
        assertEquals(task3.getName(), "Test3");
        assertEquals(task3.getDate().toString(), "Thu Mar 03 12:12:00 CST 2022");
        assertEquals(task4.getName(), "Test4");
        assertEquals(task4.getDate().toString(), "Fri Mar 04 12:12:00 CST 2022");
        assertEquals(task5.getName(), "Test5");
        assertEquals(task5.getDate().toString(), "Sat Mar 05 12:12:00 CST 2022");
    }

    /**************************************
        Tests for Algorithm Engineer
    **************************************/
    /**
     * Tests redBlackTree insert operations
     */
    @Test
    void testInsert(){
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
        tree.insert(1);
        tree.insert(5);
        tree.insert(4);
        tree.insert(3);
        tree.insert(2);
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(5));
        assertTrue(!tree.contains(0));
        assertTrue(!tree.contains(6));
    }
    /**
     * Tests redBlackTree remove operations
     */
    @Test
    void testRemove(){
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
        tree.insert(1);
        tree.insert(5);
        tree.insert(4);
        tree.insert(3);
        tree.insert(2);
        tree.remove(1);
        assertTrue(!tree.contains(1));
        tree.remove(2);
        assertTrue(!tree.contains(2));
        tree.remove(3);
        assertTrue(!tree.contains(3));
        tree.remove(4);
        assertTrue(!tree.contains(4));
        tree.remove(5);
        assertTrue(tree.isEmpty());
    }
}
