import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.util.List;

public class TaskScheduleBackend implements ITaskSchedulerBackend{

  List<ITask> data;
  
  public TaskScheduleBackend() {
    data = new ArrayList<ITask>();
  }
  
  @Override
  public boolean addTask(String dateString, String name) {
    
    Date date = new Date();
    ITask newTask = new Task(name, date);
    
    data.add(newTask);
    
    return true;
  }

  @Override
  public ITask removeTask(ITask t) {
    // TODO Auto-generated method stub
    return t;
  }

  @Override
  public List<ITask> getAllTasks() {
    // TODO Auto-generated method stub
    return data;
  }

  @Override
  public List<ITask> getOverdue() {
    // TODO Auto-generated method stub
    return data;
  }

  @Override
  public List<ITask> getBetweenDates(String minDateString, String maxDateString)
      throws ParseException {
    // TODO Auto-generated method stub
    return data;
  }

  @Override
  public void loadTree() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void saveTree() {
    // TODO Auto-generated method stub
    
  }
  

}

