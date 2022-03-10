public interface ITaskSchedulerFrontend {

	ITaskSchedulerFrontend(ITaskSchedulerBackend);
	ITaskSchedulerFrontend(ITaskSchedulerBackend, String);

	void runCommandLoop();
	public void displayCommandMenu();
	public void addTask();
	public void markTasks();
	public void displayTasks();
	public void displayOverdueTasks();
}
