import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.Iterator;
import java.util.zip.DataFormatException;

import org.junit.Test;

public class DataWranglerTests {
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
        IExtendedSortedCollection<ITask> tree;

        // Read tree, fail immediately if exception is thrown
        try {
            tree = (new TreeFileHandler()).getTreeFromFile(inputFile);
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
        IExtendedSortedCollection<ITask> tree;

        // Read tree, fail immediately if exception is thrown
        try {
            tree = (new TreeFileHandler()).getTreeFromFile(inputFile);
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
        IExtendedSortedCollection<ITask> tree;

        // Read tree, fail immediately if exception is thrown
        try {
            tree = (new TreeFileHandler()).getTreeFromFile(inputFile);
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
        IExtendedSortedCollection<ITask> tree;

        // Read tree, fail immediately if exception is thrown
        try {
            tree = (new TreeFileHandler()).getTreeFromFile(inputFile);
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
        IExtendedSortedCollection<ITask> tree;

        // Read tree, fail immediately if exception is thrown
        try {
            tree = (new TreeFileHandler()).getTreeFromFile(inputFile);
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
            newTree = new TreeFileHandler().getTreeFromFile(file);
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
}