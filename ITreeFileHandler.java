import java.io.File;

public interface ITreeFileHandler {
    /**
     * Public static method.
     * From given File f, parse the XML data and convert it to a tree.
     * @param f XML data file representing a binary search tree.
     * @return a SortedCollectionInterface object containing the XML tree's data.
     */
    SortedCollectionInterface<ITask> getTreeFromFile(File f);

    SortedCollectionInterface<ITask> writeTreeToFile(File f, SortedCollectionInterface<ITask> tree);
}
