import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.zip.DataFormatException;

/**
 * Provides methods that import and export the Task Scheduler's data
 * from an IExtendedSortedCollection tree to an XML-formatted file.
 * 
 * XML data stored in the order of the IExtendedSortedCollection tree's Iterator.
 */
public class TreeFileHandler implements ITreeFileHandler {

    /**
     * Given a File, parses the XML data and returns it in
     * the form of an IExtendedSortedCollection tree.
     * @param f XML data file representing a binary search tree.
     * @return a SortedCollectionInterface object containing the XML tree's data.
     * @throws FileNotFoundException if the file isn't found
     */
    @Override
    public IExtendedSortedCollection<ITask> getTreeFromFile(File f) throws FileNotFoundException, DataFormatException {
        IExtendedSortedCollection<ITask> tree = new RedBlackTreePlaceholderDW<ITask>();
        
        Scanner reader = new Scanner(f);

        Stack<String> openElements = new Stack<String>();

        // Tasks with no name/date in the XML will have null name/date
        String currentName = null;
        Date currentDate = null;

        while(reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            currentLine = currentLine.trim();

            if(currentLine.charAt(0) == '<') { // Start of tag

                if(currentLine.charAt(currentLine.length() - 1) != '>') {
                    throw new DataFormatException("Invalid XML: bracket not closed");
                }

                if (currentLine.charAt(1) == '/') { // Closing tag
                    String tagName = currentLine.substring(2, currentLine.length() - 1);

                    // If tag name matches currently opened tag, remove tag from openTags stack
                    if(openElements.peek().equals(tagName)) openElements.pop();
                    // Otherwise the XML is invalid
                    else throw new DataFormatException("Invalid XML: tag mismatch");

                    // If this is a task tag being closed, add a new task with previously read name/date data
                    if(tagName.equals("task")) {
                        Task task = new Task(currentName, currentDate);
                        tree.insert(task);

                        // Reset name and date to null so they can't leak into next Task read
                        currentName = null;
                        currentDate = null;
                    }
                }

                else { // Opening tag
                    // Add current tag to openTags stack
                    String tagName = currentLine.substring(1, currentLine.length() - 1);
                    openElements.push(tagName);
                }
            }
            
            else { // Content

                // Save name/date data until task tag is closed

                if(openElements.peek().equals("name")) {
                    currentName = currentLine;
                }

                else if(openElements.peek().equals("date")) {
                    int currentDateMillis = Integer.parseInt(currentLine);
                    currentDate = new Date(currentDateMillis);
                }
            }
        }

        reader.close();
        
        return tree;
    }

    /**
     * Given a File and an IExtendedSortedCollection tree, converts the
     * tree's data to XML-formatted text and outputs it to the File.
     * @param f the file to be stored to
     * @param tree the tree to be stored
     * @return true if the tree was successfully stored, false otherwise
     */
    @Override
    public boolean writeTreeToFile(File f, IExtendedSortedCollection<ITask> tree) {
        PrintWriter output;
        try {
            output = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            return false;
        }

        Iterator<ITask> tasks = tree.iterator();

        output.println("<tasks>");
        while (tasks.hasNext()) {
            ITask task = tasks.next();
            output.println("    <task>");
            output.println("        <name>");
            output.println("            " + task.getName());
            output.println("        </name>");
            output.println("        <date>");
            output.println("            " + task.getDate());
            output.println("        </date>");
            output.println("    </task>");
        }
        output.println("</tasks>");

        output.close();
        return true;
    }
}