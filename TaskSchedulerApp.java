/**
 * Runs the Task Scheduler program.
 */
public class TaskSchedulerApp {
    public static void main(String[] args) {
        ITaskSchedulerFrontend frontend = new TaskScheduleUI(new TaskSchedulerBackend());
        frontend.runCommandLoop();
    }
}
