import java.util.Date;

public class TaskPlaceholder implements ITask{
    private String name;
    private Date date;
    public TaskPlaceholder(String name, Date date){
        this.name = name;
        this.date = date;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(ITask other) {
        return date.compareTo(other.getDate());
    }

    @Override
    public String toString(){
        return name+" "+date.toString();
    }
    
}
