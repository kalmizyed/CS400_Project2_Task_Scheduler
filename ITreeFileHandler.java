// --== CS400 Project Two File Header ==--
// Name: Kaden Almizyed
// CSL Username: kaden
// Email: kalmizyed@wisc.edu
// Lecture #: 004 @4:00pm
// Notes to Grader:

import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public interface ITreeFileHandler {
    /**
     * From given File f, parse the XML data and convert it to a tree.
     * @param f XML data file representing a binary search tree.
     * @param tree an empty SortedCollectionInterface object that will contain the XML tree's data.
     * @throws FileNotFoundException
     * @throws DataFormatException
     */
    void getTreeFromFile(File f, IExtendedSortedCollection<ITask> tree) throws FileNotFoundException, DataFormatException;

    /**
     * From given SortedCollectionInterface tree, convert it
     * to an XML format and store it in the given File f.
     * @param f the file to be stored to
     * @param tree the tree to be stored
     * @return true if the tree was successfully stored, false otherwise
     */
    boolean writeTreeToFile(File f, IExtendedSortedCollection<ITask> tree);
}
