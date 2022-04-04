import java.util.Iterator;
import java.util.List;

// --== CS400 Project Two File Header ==--
// Name: Jack Blake
// CSL Username: jblake
// Email: jhblake2@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader: 

/**
 * Interface representing an iterable sorted collection which you can insert elements into and remove elements from.
 */
public interface IExtendedSortedCollection<T extends Comparable<T>> extends SortedCollectionInterface<T> {

    //The Red Black Tree I implement this with will use a default, no argument constructor.

    /**
     * Removes the node with the data for which compareTo() returns 0.
     * @param data the object to be removed
     * @return the object being removed, or null if nothing was removed
     */
    public T remove(T data);
    
    /**
     * @param min only include items where compareTo with min is greater than or equal to zero
     * @param max only include items where compareTo with max is less than or equal to zero
     * @return the list of items in the tree between min and max in order, inclusive
     */
    public List<T> getItemsBetween(T min, T max);

    /**
     * Gives an iterator that is in level order instead of in order
     * @return Itorator that has all the elements in the tree in level order
     */
    public Iterator<T> levelOrderIterator();
}
