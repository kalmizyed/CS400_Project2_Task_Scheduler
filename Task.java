import java.util.Date;

public class Task implements ITask{

  private Date date;
  private String name;
  
  public Task (String name, Date date) {
    this.name = name;
    this.date = date;
  }
  
  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getDate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int compareTo(ITask other) {
    // TODO Auto-generated method stub
    return 0;
  }
  

}

