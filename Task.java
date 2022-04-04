// --== CS400 Project Two File Header ==--
// Name: Kaden Almizyed
// CSL Username: kaden
// Email: kalmizyed@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader:

import java.util.Date;

public class Task implements ITask {
    protected String name;
    protected Date date;

    /**
     * Creates a new Task with the given name and date.
     * @param name the name of this Task
     * @param date the date of this Task
     */
    public Task(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    /**
     * @return the name of this Task
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the date of this Task
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * Compares this Task to another Task, first by date, then by name if dates are equal.
     * @return a negative integer if this Task comes before the given task, positive if it comes after, and 0 if they are equal in date and name.
     */
    @Override
    public int compareTo(ITask other) {
        int dateCompare = this.date.compareTo(other.getDate());
        if (dateCompare != 0) return dateCompare;
        return this.name.compareTo(other.getName());
    }
    
}
