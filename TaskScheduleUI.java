import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.util.Date;


public class TaskScheduleUI implements ITaskSchedulerFrontend{

    Scanner scanner;
    ITaskSchedulerBackend taskScheduler;

    public TaskScheduleUI(ITaskSchedulerBackend taskScheduler){
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void runCommandLoop() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to Task Scheduler!");
        System.out.println("==========================");
        showCommandMenu();
    }

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
      System.out.println("Display Tasks: ");
        for (ITask task : tasks)
          System.out.println(task.getName() + " | by " + task.getDate());
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
      
      taskScheduler.addTask(newDate, newName);
      
      System.out.println();
      System.out.println("Task Added! " + newName + "by " + time + " " + date);
      
      showCommandMenu();
    }

    /**
     * Displays all tasks and allows user to enter the number of the 
     * one to remove, then removes it.
     */
    @Override
    public void completeTask() {
      boolean containsName = false;
      boolean containsDate = false;
      boolean containsTime = false;
      List<ITask> taskList = taskScheduler.getAllTasks();
      
      displayTaskList(taskList);
      
      System.out.println("Enter name: ");
      String name = scanner.nextLine();
      for (ITask task : taskList) {
        if (task.getName().equals(name)) containsName = true;
      }
      
      System.out.println("Enter date and time MM/DD/YYYY HH:MM : ");
      
      String dat1 = scanner.nextLine();
      try {
        Integer.parseInt(dat1.substring(0,2));
        Integer.parseInt(dat1.substring(3,5));
        Integer.parseInt(dat1.substring(6,10));
        Integer.parseInt(dat1.substring(11,13));
        Integer.parseInt(dat1.substring(14,16));
      }
      catch(Exception e) {
        System.out.println("Invalid Date.");
        showCommandMenu();
        return;
      }
      
      Date date = stringToDate(dat1);
      for (ITask task : taskList) {
        if (task.getDate().equals(date)) containsDate = true;
      }
      
      Task completedTask = new Task(name, date);
      if (containsName && containsDate) {
        taskScheduler.removeTask(completedTask);
        System.out.println("Task " + name + " completed!");
      }
      else
        System.out.println("Task not found. Try Again.");
      showCommandMenu();
    }

    public void quit(){
        System.out.println("Quitting...");
        scanner.close();
        return;
    }

    @Override
    public void displayTasksBetweenDates() {
      System.out.println("Earliest Date MM/DD/YYYY: ");
      String min = scanner.nextLine();
      System.out.println("Latest Date MM/DD/YYYY: ");
      String max = scanner.nextLine();
      
      try {
        List<ITask> betweenList = taskScheduler.getBetweenDates(min, max);
        for (ITask task : betweenList) {
          System.out.println(task.getName() + " | by " + task.getDate());
        }
      } catch (ParseException e) {
          System.out.println("Invalid dates");
      }
      
      showCommandMenu();
    }
    
    /**
     * Turns a string into a date
     * @param str the string to turn into a date
     * @return date the Date
     */
    public Date stringToDate(String str) {
      int month = Integer.parseInt(str.substring(0,2));
      int day = Integer.parseInt(str.substring(3,5));
      int year = Integer.parseInt(str.substring(6,10));
      int hr = Integer.parseInt(str.substring(11,13));
      int min = Integer.parseInt(str.substring(14,16));
      
      Date date = new Date(month, day, year, hr, min);
      return date;
    }
} 
