// --== CS400 Project Two File Header ==--
// Name: Kaden Almizyed
// CSL Username: kaden
// Email: kalmizyed@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader:

import java.io.File;

public interface ITreeFileHandler {
    /**
     * Public static method.
     * From given File f, parse the XML data and convert it to a tree.
     * @param f XML data file representing a binary search tree.
     * @return a SortedCollectionInterface object containing the XML tree's data.
     */
    IExtendedSortedCollection<ITask> getTreeFromFile(File f);

    /**
     * Public static method.
     * From given SortedCollectionInterface tree, convert it
     * to an XML format and store it in the given File f.
     * @param f the file to be stored to
     * @param tree the tree to be stored
     * @return true if the tree was successfully stored, false otherwise
     */
    boolean writeTreeToFile(File f, IExtendedSortedCollection<ITask> tree);
}
