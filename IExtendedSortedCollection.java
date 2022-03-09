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
}
