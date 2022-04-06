// --== CS400 Project Two File Header ==--
// Name: Matthew Chang
// CSL Username: matthewc
// Email: mchang43@wisc.edu
// Lecture #: 004
// Notes to Grader: N/A

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import org.junit.jupiter.api.Test;

class FrontendDeveloperTests {

  /**
   * Test the showCommandMenu method
   */
  @Test
  void testShowCommandMenu() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    TextUITester tester;
    String output;

    //test invalid inputs
    tester = new TextUITester("q\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Invalid command.")) {
      fail("Command didn't fail with non int input");
    }
    
    tester = new TextUITester("12\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Invalid command.")) {
      fail("Command didn't fail with invalid int input");
    }
    
    //addtask
    tester = new TextUITester("1\n1\n1\n1\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Task Name: "))
      fail("Name wasn't prompted");
    if (!output.contains("Enter a date MM/DD/YYYY : "))
      fail("Date wasn't prompted");
    if (!output.contains("Enter a time HH:MM : "))
      fail("Time wasn't prompted");

    //overdue
    tester = new TextUITester("4\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Overdue Tasks: "))
      fail("Incorrect output");
    
    //between
    tester = new TextUITester("5\n1\n1\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    assertTrue(output.contains("Earliest Date MM/DD/YYYY-HH:MM:"));
    assertTrue(output.contains("Latest Date MM/DD/YYYY-HH:MM:"));
    assertTrue(output.contains("Invalid dates"));

    
    //quit
    tester = new TextUITester("6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Quitting...")) 
      fail("Command didn't quit when prompted");
  }

  /**
   * tests the displayAllTasks method
   */
  @Test
  void testDisplayAllTasks() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("3\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    
    if (!output.contains("Current Tasks: ")) 
      fail("No output");
  }
  
  /**
   * tests the displayOverdueTasks method
   */
  @Test
  void testDisplayOverdueTasks() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("4\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    
    if (!output.contains("Overdue Tasks: "))
      fail("No output");
  }
  
  /**
   * tests the completeTask method
   */
  @Test
  void testCompleteTask() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("1\nhw1\n06/04/2022\n06:30\n1\nhw2\n06/05/2022\n06:30\n2\n1\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Task successfully completed."))
      fail("Failed with good input.");
  }
  
  /**
   * tests a failure case of the addTask method
   */
  @Test
  void testAddTask() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("1\n1\n1\n1\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Invalid task. Try again.")) 
      fail("Succeeded with bad input");
  }
  
  /**
   * tests adding two of the same task to the task list, 
   * subsequently tests a valid input for the addTask method
   */
  @Test
  void testSameTask() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("1\nhw1\n06/04/2022\n06:30\n1\nhw1\n06/04/2022\n06:30\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    
    
    assertTrue(output.contains("Invalid task. Try again."));
  }
  
  /**
   * tests the displayTasksBetweenDates method
   */
  @Test
  void testDisplayTasksBetweenDates() {
    TaskSchedulerBackend backend = new TaskSchedulerBackend();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    String testString = "1\nhw1\n06/04/2022\n07:30\n5\n06/01/2022-06:30\n06/10/2022-06:30\n6\n";
    
    tester = new TextUITester(testString);
    
    frontend.runCommandLoop();
    output = tester.checkOutput();
    assertTrue(output.contains("hw1 | by Sat Jun 04 07:30:00 CDT 2022"));
    //assertTrue(output.contains("hw2 | by Sun Jun 05 06:30:00 CDT 2022"));
  }
  
  /**
   * Tests another case of the Task's compareTo method with dates
   */
  @Test
  void testTaskCompareToDate() {
    long currentTimeMillis = System.currentTimeMillis();


    Task t1 = new Task("Task", new Date(currentTimeMillis));

    Task t2 = new Task("Task", new Date(currentTimeMillis + 1));
    
    assertTrue(t2.compareTo(t1) > 0);
  }

  /**
   * Tests another case of the Task's compareTo method with names
   */
  @Test
  void testTaskCompareToName() {
    Date currentTime = new Date(System.currentTimeMillis());

    Task t1 = new Task("a", currentTime);

    Task t2 = new Task("b", currentTime);

    assertTrue(t2.compareTo(t1) > 0);
    
  }
}


