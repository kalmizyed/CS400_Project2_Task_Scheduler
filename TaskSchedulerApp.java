/**
 * Runs the Task Scheduler program.
 */
public class TaskSchedulerApp {
    public static void main(String[] args) {
        // TODO replace null with TaskSchedulerFrontend when code is merged
        ITaskSchedulerFrontend frontend = new TaskScheduleUI(new TaskScheduleBackend());
        frontend.runCommandLoop();
    }
}
