// --== CS400 Project Two File Header ==--
// Name: Kaden Almizyed
// CSL Username: kaden
// Email: kalmizyed@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader:

import java.util.Date;

public interface ITask extends Comparable<ITask> {

    // Constructor will be in the form (String name, Date date)

    /**
     * @return the name of the task
     */
    String getName();

    /**
     * @return the task's date
     */
    Date getDate();

    /**
     * Compares in ascending order based on date, and if the dates are equal, compares lexicographically by name.
     * @param other the Task to be compared against
     * @return Negative if this task is less than the other, positive if greater, 0 if equal
     */
    int compareTo(ITask other);

    /**
     * @return the String representation of this Task.
     */
    String toString();
}
