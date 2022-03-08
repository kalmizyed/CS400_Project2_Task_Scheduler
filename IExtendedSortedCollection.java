public interface IExtendedSortedCollection<T extends Comparable<T>> extends SortedCollectionInterface<T> {
    /**
     * Removes the node with the data for which compareTo() returns 0.
     * @param data the object to be removed
     * @return the object being removed, or null if nothing was removed
     */
    T remove(T data);
}
