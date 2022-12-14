// --== CS400 Project Two File Header ==--
// Name: Matthew Chang
// CSL Username: matthewc
// Email: mchang43@wisc.edu
// Lecture #: 004
// Notes to Grader: N/A

import java.util.List;
import java.util.Scanner;
import java.text.ParseException;

public class TaskScheduleUI implements ITaskSchedulerFrontend{

    Scanner scanner;
    ITaskSchedulerBackend taskScheduler;

    /**
     * Constructor for a TaskScheduleUI object
     * @param taskScheduler the backend database
     */
    public TaskScheduleUI(ITaskSchedulerBackend taskScheduler){
        this.taskScheduler = taskScheduler;
        try {
          taskScheduler.loadTree();
        } catch (Exception e) {}
    }

    /**
     * Outputs the introduction to teh program
     */
    @Override
    public void runCommandLoop() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to Task Scheduler!");
        System.out.println("==========================");
        showCommandMenu();
    }

    /**
     * Shows the command menu that the use can choose from
     */
    @Override
    public void showCommandMenu() {
        System.out.println("Command Menu:");
        System.out.println("    1) Add Task");        
        System.out.println("    2) Mark Task as Complete");        
        System.out.println("    3) Display Tasks");        
        System.out.println("    4) Display Overdue Tasks"); 
        System.out.println("    5) Display Tasks between Dates");
        System.out.println("    6) Quit");        

        //Make sure command is only one character

        System.out.println("Choose a command from the menu above: ");
        String inputChar = scanner.nextLine();
        try {
          Integer.parseInt(inputChar);
          if (inputChar.length() != 1) throw new NumberFormatException();
        }
        catch (Exception e) {
          System.out.println("Invalid command.");
          showCommandMenu();
        }
        
        switch (inputChar){
            case "1": 
                addTask();
                break;
            case "2":
                completeTask();
                break;
            case "3": 
                displayAllTasks();
                break;
            case "4":
                displayOverdueTasks();
                break;
            case "5":
                displayTasksBetweenDates();
                break;
            case "6":
                quit();
                break;
        }
    }

    /**
     * Displays all tasks.  Makes use of displayTaskList().
     */
    @Override
    public void displayAllTasks() {
      displayTaskList(taskScheduler.getAllTasks());
      showCommandMenu();
    }

    /**
     * displays Overdue tasks
     */
    @Override
    public void displayOverdueTasks() {
         List<ITask> overdueList = taskScheduler.getOverdue();
         System.out.println("Overdue Tasks: ");
         for (ITask task : overdueList) {
           System.out.println(task.getName() + " | by " + task.getDate());
         }
         showCommandMenu();
    }

    /**
     * Displays a given list of tasks
     * @param tasks
     */
    @Override
    public void displayTaskList(List<ITask> tasks) {
      System.out.println();
      System.out.println("Current Tasks: ");
        for (ITask task : tasks)
          System.out.println(task.getName() + " | by " + task.getDate());
        System.out.println();
    }

    /**
     * adds task based on user input about due date and assignment name
     */
    @Override
    public void addTask() {
      System.out.println("Task Name: ");
      String newName = scanner.nextLine();
      
      //Make the date
      System.out.println("Enter a date MM/DD/YYYY : ");
      String date = scanner.nextLine();
      
      System.out.println("Enter a time HH:MM : ");
      String time = scanner.nextLine();

      String newDate = date + "-" + time;
      
      if (taskScheduler.addTask(newDate, newName)) {
        System.out.println(newDate);
        System.out.println();
        System.out.println("Task Added! " + newName + " by " + time + " " + date);
        System.out.println();
        System.out.println("hi");
      }
      else {
        System.out.println();
        System.out.println("Invalid task. Try again.");
      }
      
      showCommandMenu();
    }

    /**
     * Displays all tasks and allows user to enter the number of the 
     * one to remove, then removes it.
     */
    @Override
    public void completeTask() {
      List<ITask> taskList = taskScheduler.getAllTasks();
      
      int iterate = 0;
      
      if(!taskList.isEmpty()) {
        for (ITask task : taskList) {
          iterate++;
          System.out.println(iterate + ": " + task.getName() + " | by " + task.getDate());
        }
        
        System.out.println("Which task did you complete? (Enter task number): ");
        int taskNum = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (taskNum > taskList.size() || taskNum < 0) 
          System.out.println("Invalid task number. Try Again.");
        else {
          for (int i = 0; i < taskList.size(); i++) {
            if (i == taskNum) {
              taskScheduler.removeTask(taskList.get(i));
              System.out.println("Task successfully completed.");
              System.out.println();
            }
          }
        }
      }
      else System.out.println("No tasks to complete.");
      
      System.out.println();
      
      showCommandMenu();
    }

    /**
     * Quits the program
     */
    public void quit(){
        System.out.println("Quitting...");
        scanner.close();
        taskScheduler.saveTree();
        return;
    }

    /**
     * Displays a task between two inputed dates
     */
    @Override
    public void displayTasksBetweenDates() {
      System.out.println("Earliest Date MM/DD/YYYY-HH:MM: ");
      String min = scanner.nextLine();
      System.out.println("Latest Date MM/DD/YYYY-HH:MM: ");
      String max = scanner.nextLine();
      
      try {
        List<ITask> betweenList = taskScheduler.getBetweenDates(min, max);
        
        System.out.println();
        System.out.println("Tasks: ");
        for (ITask task : betweenList) {
          System.out.println(task.getName() + " | by " + task.getDate());
        }
      } catch (ParseException e) {
          System.out.println("Invalid dates");
      }
      System.out.println();
      
      showCommandMenu();
    }
} 

