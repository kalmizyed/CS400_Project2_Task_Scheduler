// --== CS400 Project Two File Header ==--
// Name: Kaden Almizyed
// CSL Username: kaden
// Email: kalmizyed@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader:

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import org.junit.Test;

public class DataWranglerTests {
    
    /**************/
    /* UNIT TESTS */
    /**************/
    
    /**
     * Tests all constructors and getter methods of the Task class.
     */
    @Test
    public void testTask() {
        String testName = "Name";
        Date testDate = new Date(System.currentTimeMillis());
        Task task = new Task(testName, testDate);

        assertEquals(testName, task.getName());
        assertEquals(testDate, task.getDate());
    }

    /**
     * Tests the compareTo() method of the Task class on two tasks with differing dates.
     */
    @Test
    public void testTaskCompareToDate() {
        long currentTimeMillis = System.currentTimeMillis();

        // Create task at current time
        Task firstTask = new Task("Task", new Date(currentTimeMillis));

        // Create task at a later time
        Task secondTask = new Task("Task", new Date(currentTimeMillis + 1));

        assertTrue(firstTask.compareTo(secondTask) < 0);
    }

    /**
     * Tests the compareTo() method of the Task class on two tasks with differing names.
     */
    @Test
    public void testTaskCompareToName() {
        Date currentTime = new Date(System.currentTimeMillis());

        // Create task at current time
        Task firstTask = new Task("a", currentTime);

        // Create task at a later time
        Task secondTask = new Task("b", currentTime);

        assertTrue(firstTask.compareTo(secondTask) < 0);
    }

    /**
     * Tests the compareTo() method of the Task class on two equal tasks.
     */
    @Test
    public void testTaskCompareToEqual() {
        Date currentTime = new Date(System.currentTimeMillis());

        // Create task at current time
        Task firstTask = new Task("Task", currentTime);

        // Create task at a later time
        Task secondTask = new Task("Task", currentTime);

        assertTrue(firstTask.compareTo(secondTask) == 0);
    }

    /**
     * Tests the writeTreeToFile method of the TreeFileHandler class with a valid input tree.
     */
    @Test
    public void testWriteGoodTree() {
        RedBlackTreePlaceholderDW<ITask> tree = new RedBlackTreePlaceholderDW<ITask>();

        // Add 10 tasks to the tree, differing in name and date
        for(int i = 0; i < 10; i++) {
            Task task = new Task(Integer.toString(i), new Date(System.currentTimeMillis()));
            tree.insert(task);
        }

        File outputFile = new File("testOutputValid.xml");

        // Call writeTreeToFile on a new TreeFileHandler
        boolean successfulWrite = new TreeFileHandler().writeTreeToFile(outputFile, tree);

        assertTrue(successfulWrite);
    }

    /**
     * Tests the writeTreeToFile method of the TreeFileHandler class with an empty tree.
     */
    @Test
    public void testWriteEmptyTree() {
        RedBlackTreePlaceholderDW<ITask> tree = new RedBlackTreePlaceholderDW<ITask>();

        // Add nothing to the tree

        File outputFile = new File("testOutputEmptyTree.xml");

        // Call writeTreeToFile on a new TreeFileHandler
        boolean successfulWrite = new TreeFileHandler().writeTreeToFile(outputFile, tree);

        assertTrue(successfulWrite);
    }

    /**
     * Tests the getTreeFromFile method of the TreeFileHandler class with a valid XML file.
     */
    @Test
    public void testReadGoodXML() {
        File inputFile = new File("testInputValid.xml");
        IExtendedSortedCollection<ITask> tree = new RedBlackTreePlaceholderDW<>();

        // Read tree, fail immediately if exception is thrown
        try {
            (new TreeFileHandler()).getTreeFromFile(inputFile, tree);
        } catch (FileNotFoundException | DataFormatException e) {
            fail(e);
            return;
        }

        // Validate aspects of the tree
        assertEquals(3, tree.size());
        assertTrue(tree.contains(new Task("Test that this task works", new Date(1648502886275l))));
        assertTrue(tree.contains(new Task("Test task 2", new Date(1648502986275l))));
        assertTrue(tree.contains(new Task("Testing the third", new Date(1648503886275l))));
    }

    /**
     * Tests the getTreeFromFile method of the TreeFileHandler class with empty tasks.
     */
    @Test
    public void testReadEmptyTasks() {
        File inputFile = new File("testInputInvalidEmptyTasks.xml");
        IExtendedSortedCollection<ITask> tree = new RedBlackTreePlaceholderDW<>();

        // Read tree, fail immediately if exception is thrown
        try {
            (new TreeFileHandler()).getTreeFromFile(inputFile, tree);
        } catch (FileNotFoundException e) {
            fail(e);
            return;
        } catch (DataFormatException e) {
            return;
        }

        fail("Should have thrown DataFormatException.");
    }

    /**
     * Tests the getTreeFromFile method of the TreeFileHandler class with invalid tag names.
     */
    @Test
    public void testReadInvalidTagNames() {
        File inputFile = new File("testInputInvalidTagNames.xml");
        IExtendedSortedCollection<ITask> tree = new RedBlackTreePlaceholderDW<>();

        // Read tree, fail immediately if exception is thrown
        try {
            (new TreeFileHandler()).getTreeFromFile(inputFile, tree);
        } catch (FileNotFoundException | DataFormatException e) {
            fail(e);
            return;
        }

        // Validate aspects of the tree
        assertEquals(0, tree.size());
    }

    /**
     * Tests the getTreeFromFile method of the TreeFileHandler class with tags left unclosed at the end of the file.
     */
    @Test
    public void testReadTagsOpen() {
        File inputFile = new File("testInputInvalidTagsOpen.xml");
        IExtendedSortedCollection<ITask> tree = new RedBlackTreePlaceholderDW<>();

        // Read tree, fail immediately if exception is thrown
        try {
            (new TreeFileHandler()).getTreeFromFile(inputFile, tree);
        } catch (DataFormatException e) {
            return;
        } catch (FileNotFoundException e) {
            fail(e);
        }

        fail("Should have thrown DataFormatException");
    }

    /**
     * Tests the getTreeFromFile method of the TreeFileHandler class with tag written with invalid syntax, i.e. "<task" or "task>"
     */
    @Test
    public void testReadInvalidTagSyntax() {
        File inputFile = new File("testInputInvalidTagSyntax.xml");
        IExtendedSortedCollection<ITask> tree = new RedBlackTreePlaceholderDW<>();

        // Read tree, fail immediately if exception is thrown
        try {
            (new TreeFileHandler()).getTreeFromFile(inputFile, tree);
        } catch (DataFormatException e) {
            return;
        } catch (FileNotFoundException e) {
            fail(e);
        }

        fail("Should throw DataFormatException");
    }

    /**
     * Tests that the writeTreeToFile() and getTreeFromFile() methods from the TreeFileHandler class work in conjunction with one another.
     */
    @Test
    public void testOutputAndInput() {
        RedBlackTreePlaceholderDW<ITask> tree = new RedBlackTreePlaceholderDW<ITask>();
        File file = new File("testInputOutput.xml");

        // Add 10 elements to the tree
        for(int i = 0; i < 10; i++) {
            tree.insert(new Task(Integer.toString(i), new Date(System.currentTimeMillis())));
        }

        // Write tree to file
        new TreeFileHandler().writeTreeToFile(file, tree);

        // Read tree from file
        IExtendedSortedCollection<ITask> newTree = new RedBlackTreePlaceholderDW<ITask>();
        try {
            new TreeFileHandler().getTreeFromFile(file, newTree);
        } catch (FileNotFoundException | DataFormatException e) {
            fail(e);
        }

        // Validate trees are the same
        assertEquals(tree.size(), newTree.size()); // size

        Iterator<ITask> treeIterator = tree.iterator();
        Iterator<ITask> newTreeIterator = newTree.iterator();

        while (treeIterator.hasNext()) {
            assertEquals(0, treeIterator.next().compareTo(newTreeIterator.next()));
        }
    }

    /*********************/
    /* INTEGRATION TESTS */
    /*********************/

    /**
     * Tests the writeTreeToFile() and getTreeFromFile() methods separately,
     * working with the RedBlackTree class written by the Algorithm Engineer.
     */
    @Test
    public void testReadAndWriteIndividualIntegrated() {

        // Test reading

        File inputFile = new File("testInputValid.xml");
        IExtendedSortedCollection<ITask> tree = new RedBlackTree<>();

        // Read tree, fail immediately if exception is thrown
        try {
            (new TreeFileHandler()).getTreeFromFile(inputFile, tree);
        } catch (FileNotFoundException | DataFormatException e) {
            fail(e);
            return;
        }

        // Validate aspects of the tree
        assertEquals(3, tree.size());
        assertTrue(tree.contains(new Task("Test that this task works", new Date(1648502886275l))));
        assertTrue(tree.contains(new Task("Test task 2", new Date(1648502986275l))));
        assertTrue(tree.contains(new Task("Testing the third", new Date(1648503886275l))));

        // Test writing

        tree = new RedBlackTree<ITask>();

        // Add 10 tasks to the tree, differing in name and date
        for(int i = 0; i < 10; i++) {
            Task task = new Task(Integer.toString(i), new Date(System.currentTimeMillis()));
            tree.insert(task);
        }

        File outputFile = new File("testOutputValid.xml");

        // Call writeTreeToFile on a new TreeFileHandler
        boolean successfulWrite = new TreeFileHandler().writeTreeToFile(outputFile, tree);

        assertTrue(successfulWrite);
    }

    /**
     * Tests that the writeTreeToFile() and getTreeFromFile() methods from the TreeFileHandler class work in conjunction with one another.
     */
    @Test
    public void testOutputAndInputIntegrated() {
        RedBlackTree<ITask> tree = new RedBlackTree<ITask>();
        File file = new File("testInputOutput.xml");

        // Add 10 elements to the tree
        for(int i = 0; i < 10; i++) {
            tree.insert(new Task(Integer.toString(i), new Date(System.currentTimeMillis())));
        }

        // Write tree to file
        new TreeFileHandler().writeTreeToFile(file, tree);

        // Read tree from file
        RedBlackTree<ITask> newTree = new RedBlackTree<ITask>();
        try {
            new TreeFileHandler().getTreeFromFile(file, newTree);
        } catch (FileNotFoundException | DataFormatException e) {
            fail(e);
        }

        // Validate trees are the same
        assertEquals(tree.size(), newTree.size()); // size

        Iterator<ITask> treeIterator = tree.iterator();
        Iterator<ITask> newTreeIterator = newTree.iterator();

        while (treeIterator.hasNext()) {
            assertEquals(0, treeIterator.next().compareTo(newTreeIterator.next()));
        }
    }

    /*********************/
    /* CODE REVIEW TESTS */
    /*********************/

    // Code review partner: Matthew Chang (Frontend Developer)

    /**
     * Tests that the frontend saves the current state of the tree when quitting.
     */
    @Test
    public void testFileSaved() {
        TextUITester tester;
        TaskScheduleUI frontend = new TaskScheduleUI(new TaskSchedulerBackend());

        tester = new TextUITester("1\nFinish Semester\n05/05/2022\n23:59\n6\n");
        frontend.runCommandLoop();
        tester.checkOutput();

        File dataFile = new File("data.xml");

        // Test that the output file contains the Task name
        try {
            Scanner data = new Scanner(dataFile);
            boolean containsTask = false;
            while(data.hasNext()) {
                if (data.next().contains("Finish Semester")) containsTask = true;
            }
            data.close();
            assertTrue(containsTask);
        } catch (FileNotFoundException e) {
            fail(e);
        }
        
    }

    /**
     * Tests that the frontend loads the saved state of the tree when starting.
     */
    @Test
    public void testFileLoaded() {
        // Run the frontend and save a task
        TaskScheduleUI frontend1 = new TaskScheduleUI(new TaskSchedulerBackend());
        TextUITester tester1 = new TextUITester("1\nFinish finals\n13/05/2022\n23:59\n6\n");
        frontend1.runCommandLoop();
        tester1.checkOutput();

        // Run the frontend again and display Tasks
        TaskScheduleUI frontend2 = new TaskScheduleUI(new TaskSchedulerBackend());
        TextUITester tester2 = new TextUITester("3\n6\n");
        frontend2.runCommandLoop();
        String output = tester2.checkOutput();

        // Frontend should have printed the Task name
        assertTrue(output.contains("Finish finals"));
    }
}
