// --== CS400 File Header Information ==--
// Name: Jack Blake
// Email: jhblake2@wisc.edu
// Team: DC
// TA: April Roszkowski
// Lecturer: Florian Heimerl
// Notes to Grader: 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements IExtendedSortedCollection<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public int blackHeight;
        public Node(T data) { this.data = data; }
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        public String getText(){
            return this.data.toString() + ":" + this.blackHeight;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; size++; root.blackHeight = 1; return true; } // add first node to an empty tree
        else{
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) size++;
            else throw new IllegalArgumentException(
                    "This RedBlackTree already contains that value.");
            this.root.blackHeight = 1;
            return returnValue;
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

            // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * Resolves any Red Black Tree rule violations using color swaps and rotations (sometimes recursively) 
     * @param node at which to check for and resolve violations
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> node){
        //do not need to check if the node is null because this method will never be called with that as a parameter
        if(node == this.root) return;
        Node<T> parent = node.parent;
        if(parent.blackHeight > 0) return;
        if(parent == this.root) return;
        Node<T> grandParent = node.parent.parent;
        Node<T> uncle;
        if(parent.isLeftChild()) uncle = grandParent.rightChild;
        else uncle = grandParent.leftChild;

        //if parent and uncle are red set parent's and uncle's
        //black heights to 1 and the grandparents black height to 0. Then recurse at the grandparent.
        //if uncle is null, we consider it to be black
        if( uncle != null && uncle.blackHeight == 0) {
            parent.blackHeight = 1;
            uncle.blackHeight = 1;
            grandParent.blackHeight = 0;
            enforceRBTreePropertiesAfterInsert(grandParent);
            return;
        }

        //if child and parent are not childs of the same side, rotate them so that they are.
        //then recurse at the parent (which is now the child)
        if(node.isLeftChild() != parent.isLeftChild()){
            this.rotate(node, parent);
            enforceRBTreePropertiesAfterInsert(parent);
            return;
        }

        //if child and parent are children of the same side, rotate and color swap parent and grandparent
        rotate(parent, grandParent);
        int tmp = grandParent.blackHeight;
        grandParent.blackHeight = parent.blackHeight;
        parent.blackHeight = tmp;
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if(parent == null) throw new IllegalArgumentException("null parent");
        if(child == null) throw new IllegalArgumentException("null child");
        if(!(parent.leftChild == child || parent.rightChild == child)) 
            throw new IllegalArgumentException("child is not one of parent's children");
        if(child.isLeftChild()){
            //right rotation
            //Tree looks like this: a, b, and c are subtrees or nulls
            /*
                           |
                         parent
                        /     \
                    child      c
                    /   \
                   a     b
            */

            //want to get it to:

            /*
                           |
                         child
                        /     \
                       a     parent
                              /   \
                             b     c
            */
            
            // a and c already in positon relative to their parents

            Node<T> b = child.rightChild; //leftChild for left rotation

            //if parent is the root, set the root to child
            //otherwise set parent's parent's appropriate leftChild/rightChild reference to child
            //parent won't get garbage collected because of this method's local reference
            if(parent == this.root) this.root = child;
            else {
                if(parent.isLeftChild()) parent.parent.leftChild = child;
                else parent.parent.rightChild = child;
            }
            //set child's rightChild reference to parent. 
            //No garbage collection issues since we have a reference to b from earlier
            child.rightChild = parent; //leftChild for left rotation
            //set parent's leftChild reference to b
            parent.leftChild = b; //rightChild for left rotation
            //set b's parent to parent if b is not null
            if(b != null) b.parent = parent;
            //swap parents
            Node<T> tmp = parent.parent;
            parent.parent = child;
            child.parent = tmp;

        } else {
            //left rotation
            //apply same logic as right rotation, make changes as commented above
            Node<T> b = child.leftChild;

            if(parent == this.root) this.root = child;
            else {
                if(parent.isLeftChild()) parent.parent.leftChild = child;
                else parent.parent.rightChild = child;
            }

            child.leftChild = parent;
            parent.rightChild = b;
            if(b != null) b.parent = parent;

            Node<T> tmp = parent.parent;
            parent.parent = child;
            child.parent = tmp;
        }
    }
    /**
     * Removes the node with the given data from the tree (using compareTo, not ==)
     * @param data data that a node must match to be removed 
     * @return the removed node, or null if the node is node in the tree
     */
    @Override
    public T remove(T data) {
        Node<T> toRemove = findNodeToRemove(data, root);
        if(toRemove == null) return null;
        System.out.println("Removing " + toRemove.data + "...");
        return removeHelper(toRemove);
    }
    /**
     * @param data of the node to remove
     * @param node currently being checked
     * @return the node that matched the given data with compareTo
     */
    protected Node<T> findNodeToRemove(T data, Node<T> node){
        if(node == null) return null;
        //if the node's data is bigger, search the left subtree is it exists
        if(node.data.compareTo(data) > 0) {
            if(node.leftChild == null) return null;
            return findNodeToRemove(data, node.leftChild);
        }
        //if the node's data is smaller, search the right subtree if it exists
        if(node.data.compareTo(data) < 0){
            if(node.rightChild == null) return null;
            return findNodeToRemove(data, node.rightChild);
        }
        //if node's data is not bigger or smaller, it must be equal, so return node's data
        return node;
    }
    /**
     * Helper for the remove method that removes the Node passed in
     * @param node to remove
     * @return the data of the removed node
     */
    protected T removeHelper(Node<T> node){
        //no children
        if(node.leftChild == null && node.rightChild == null){
            System.out.println("Case 0");
            //if the node is the root, simply delete it
            if(node == root) {
                root = null;
                this.size--;
                return node.data;
            }
            //if the node is red, simply delete it
            if(node.blackHeight == 0){
                deleteNode(node);
                this.size--;
                return node.data;
            }
            //if the node is black, set it to a double black, resolve the double black, then delete the node
            node.blackHeight = 2;
            resolveDoubleBlack(node);
            deleteNode(node);
            this.size--;
            return node.data;
        }
        T data = node.data;
        //one child, replace node's data with the child's then delete the child
        if(node.rightChild == null){
            System.out.println("Case 1");
            node.data = node.leftChild.data;
            deleteNode(node.leftChild);
            this.size--;
            return data;
        }
        if(node.leftChild == null){
            System.out.println("Case 1");
            node.data = node.rightChild.data;
            deleteNode(node.rightChild);
            this.size--;
            return data;
        }
        //two children
        System.out.println("Case 2");
        //find successor
        Node<T> suc = node.rightChild;
        while(suc.leftChild != null){
            suc = suc.leftChild;
        }
        //replace node's data with successor's data, then recursivly remove successor
        node.data = suc.data;
        removeHelper(suc);
        return data;
    }
    protected void deleteNode(Node<T> node){
        if(node.isLeftChild()){
            node.parent.leftChild = null;
            return;
        }
        node.parent.rightChild = null;
    }
    /**
     * Prepares the tree for removal of a black leaf node by resolving the double black node (sometime recursivly)
     * @param db reference to the double black node
     */
    protected void resolveDoubleBlack(Node<T> db){
        Node<T> parent = db.parent;
        Node<T> sibling;
        if(db.isLeftChild()) sibling = parent.rightChild;
        else sibling = parent.leftChild;
        System.out.println("db: " + db.getText());
        System.out.println("parent: " + parent.getText());
        System.out.println("sibling: " + sibling.getText());
        //double black has red sibling, rotate and color swap sibling and parent, then recurse at the double black
        if(sibling.blackHeight == 0){
            System.out.println("DoubleBlack Case 2");
            rotate(sibling, parent);
            int tmp = parent.blackHeight;
            parent.blackHeight = sibling.blackHeight;
            sibling.blackHeight = tmp;
            resolveDoubleBlack(db);
            return;
        }
        //double black's sibling has no red children
        if((sibling.leftChild == null || sibling.leftChild.blackHeight == 1)
            && (sibling.rightChild == null || sibling.rightChild.blackHeight == 1)){
                System.out.println("DoubleBlack Case 1");
                //substract 1 from the double black and the sibling's black heights, add one to the parents
                db.blackHeight--;
                sibling.blackHeight--;
                parent.blackHeight++;
                //if the parent was originally red, or is the root, all double black is resolved, otherwise recurse at parent
                if(parent.blackHeight == 1) return;
                if(parent == root) {
                    root.blackHeight = 1;
                    return;
                }
                //parent is double black and not root, so recurse at parent
                resolveDoubleBlack(parent);
                return;
        }
        //double black's sibling is black with at least one red child
        System.out.println("DoubleBlack Case 0");
        //sibling's only red child is oriented inward, rotate and color swap sibling and its inner child
        if(sibling.isLeftChild() && (sibling.leftChild == null || sibling.leftChild.blackHeight == 1)){
            int tmp = sibling.blackHeight;
            sibling = sibling.parent;
            sibling.blackHeight = sibling.leftChild.blackHeight;
            sibling.leftChild.blackHeight = tmp;
            rotate(sibling.rightChild, sibling);
        } else if(!sibling.isLeftChild() && (sibling.rightChild == null || sibling.rightChild.blackHeight == 1)){
            rotate(sibling.leftChild, sibling);
            sibling = sibling.parent;
            int tmp = sibling.blackHeight;
            sibling.blackHeight = sibling.rightChild.blackHeight;
            sibling.rightChild.blackHeight = tmp;
        }
        //now outside child of sibling must be red

        //recolor
        db.blackHeight--;
        if(sibling.isLeftChild()) sibling.leftChild.blackHeight++;
        else sibling.rightChild.blackHeight++;

        //rotate and color swap parent and sibling
        rotate(sibling, parent);
        int tmp = sibling.blackHeight;
        sibling.blackHeight = parent.blackHeight;
        parent.blackHeight = tmp;
    }

    /**
     * @param min all elements in the returned list must be greater than or equal to this
     * @param max all elements in the returned list must be less than or equal to this
     * @return a List of items between min and max
     */
    @Override
    public List<T> getItemsBetween(T min, T max){
        List<T> list = new ArrayList<T>();
        for(T data: this){
            if(data.compareTo(min) >= 0 && data.compareTo(max) <= 0) list.add(data);
        }
        return list;
    }

    /**
     * Gives an iterator that is in level order instead of in order
     * @return Itorator that has all the elements in the tree in level order
     */
    public Iterator<T> levelOrderIterator() {
        LinkedList<Node<T>> q = new LinkedList<>();
        q.add(this.root);
        LinkedList<T> levelOrderList = new LinkedList<T>();
        while(!q.isEmpty()){
            Node<T> next = q.removeFirst();
            if(next.leftChild != null) q.add(next.leftChild);
            if(next.rightChild != null) q.add(next.rightChild);
            levelOrderList.add(next.data);
        }
        if(levelOrderList.getFirst() instanceof Task){
            System.out.print("[");
            for(T data : levelOrderList){
                System.out.print(" " + ((Task)data).getName() + " ");
            }
            System.out.println("]");
        }
        return levelOrderList.iterator();
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in un8til we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }

            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements / steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()) );
            }

        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level
     * order traversal. The toString of the RedBlackTree class below
     * produces an inorder traversal of the nodes / values of the tree.
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        LinkedList<Node<T>> q = new LinkedList<>();
        q.add(this.root);
        while(!q.isEmpty()) {
            Node<T> next = q.removeFirst();
            if(next.leftChild != null) q.add(next.leftChild);
            if(next.rightChild != null) q.add(next.rightChild);
            output += next.data.toString();
            if(!q.isEmpty()) output += ", ";
        }
        return output + " ]";
    }


    @Override
    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

}
