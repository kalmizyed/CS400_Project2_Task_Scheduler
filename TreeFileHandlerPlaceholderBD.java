import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TreeFileHandlerPlaceholderBD implements ITreeFileHandler{

    @Override
    public IExtendedSortedCollection<ITask> getTreeFromFile(File f) {
        if(f.exists()){
            IExtendedSortedCollection<ITask> tree = new RedBlackTree<ITask>();
            tree.insert(new TaskPlaceholder("Task1", new Date(2022,3,28,12,12)));
            tree.insert(new TaskPlaceholder("Task1", new Date(2022,3,27,12,12)));
            tree.insert(new TaskPlaceholder("Task1", new Date(2022,3,29,12,12)));
            tree.insert(new TaskPlaceholder("Task1", new Date(2022,3,26,12,12)));
            return tree;
        }
        else return null;
        
    }

    @Override
    public boolean writeTreeToFile(File f, IExtendedSortedCollection<ITask> tree) {
        try{
            FileWriter fw = new FileWriter(f);
            fw.write("Test");
            fw.close();
        }
        catch(IOException e) {return false;}
        return true;
    }
    
}
