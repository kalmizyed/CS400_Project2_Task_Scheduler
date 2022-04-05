import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FrontendDeveloperTests {

  @Test
  void testShowCommandMenu() {
    TaskSchedulerBackendPlaceholderFD backend = new TaskSchedulerBackendPlaceholderFD();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    TextUITester tester;
    String output;

    //test invalid inputs
    tester = new TextUITester("q\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Invalid command.")) {
      fail("Command didn't fail with non int input");
      //System.out.println("failed1");
      //return false;
    }
    
    tester = new TextUITester("12\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Invalid command.")) {
      fail("Command didn't fail with invalid int input");
      //System.out.println("failed2");
      //return false;
    }
    
    //quit
    tester = new TextUITester("6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Quitting...")) 
      fail("Command didn't quit when prompted");
      //return false;
    
    //addtask
    tester = new TextUITester("1\n1\n1\n1\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Task Name: "))
      fail("Name wasn't prompted");
      //return false;
    if (!output.contains("Enter a date MM/DD/YYYY : "))
      fail("Date wasn't prompted");
      //return false;
    if (!output.contains("Enter a time HH:MM : "))
      fail("Time wasn't prompted");
      //return false;
    
    //return true;
  }

  @Test
  void testDisplayAllTasks() {
    TaskSchedulerBackendPlaceholderFD backend = new TaskSchedulerBackendPlaceholderFD();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("3\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    
    if (!output.contains("Display Tasks: ")) 
      fail("No output");
      //return false;
    
    //return true;
  }
  
  @Test
  void testDisplayOverdueTasks() {
    TaskSchedulerBackendPlaceholderFD backend = new TaskSchedulerBackendPlaceholderFD();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("4\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    
    if (!output.contains("Overdue Tasks: "))
      fail("No output");
      //return false;
    //return true;
  }
  
  @Test
  void testCompleteTask() {
    TaskSchedulerBackendPlaceholderFD backend = new TaskSchedulerBackendPlaceholderFD();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("2\nhi\n2\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Invalid Date"))
      fail("Didn't fail with invalid date.");
      //return false;
    
    tester = new TextUITester("2\nhi\n09/08/2002 06:30\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Task not found. Try Again.")) 
      fail("Didn't fail with task not in the database.");
      //return false;
    
    //return true;
  }
  
  @Test
  void testAddTask() {
    TaskSchedulerBackendPlaceholderFD backend = new TaskSchedulerBackendPlaceholderFD();
    TaskScheduleUI frontend = new TaskScheduleUI(backend);
    
    String output;
    TextUITester tester;
    
    tester = new TextUITester("1\nhi\nhi\nhi\n6\n");
    frontend.runCommandLoop();
    output = tester.checkOutput();
    if (!output.contains("Task Added! hiby hi hi")) 
      fail("Incorrect output");
      //return false;
    
    //return true;
  }
}

