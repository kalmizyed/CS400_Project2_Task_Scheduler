// --== CS400 File Header Information ==--
// Name: Jack Blake
// Email: jhblake2@wisc.edu
// Team: DC
// TA: April Roszkowski
// Lecturer: Florian Heimerl
// Notes to Grader: Not using level order traversal for testing because it doesn't consider parent references (which I was having bugs with)
//Also, disclaimer that many of the trees I use for testing are not balanced, they are just done that way so I don't have to add tons of nodes

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmEngineerTests {
    private static RedBlackTree<Integer> tree;
    @BeforeEach
    public void init(){
        tree = new RedBlackTree<Integer>();
    }
    /**
     * Tests the getItemsBetween method of the RedBlackTree class in varius cases
     */
    @Test
    public void test1(){

        //Case 0: normal case, make sure the range is inclusive
        //////////////////////////////////////////////////////////////////////////
        for(int i = 0; i < 20; i++){
            tree.insert(i);
        }
        List<Integer> list = tree.getItemsBetween(4, 9);
        assertEquals(6, list.size());
        for(int i = 4; i < 10; i++){
            assertEquals(true, list.contains(i));
        }
        //Case 1: bottom and top of the range are the same, returned list should only have that item
        //////////////////////////////////////////////////////////////////////////
        list = tree.getItemsBetween(3, 3);
        assertEquals(1, list.size());
        assertEquals(3, list.get(0));

        //Case 2: No items in range
        //////////////////////////////////////////////////////////////////////////
        list = tree.getItemsBetween(23, 30);
        assertEquals(0, list.size());

        //Case 3: top of range is less than the bottom of the range, should return empty list
        //////////////////////////////////////////////////////////////////////////
        list = tree.getItemsBetween(12, 10);
        assertEquals(0, list.size());
    }

    /**
     * Tests the non edge cases cases for the remove method of the RedBlackTree class that do not involve a double black (Whitebox)
     */
    @Test
    public void test2(){
        //Case 0: removing red node with no children
        ////////////////////////////////////////////////////////////////////////////////////
        /*
        Original Tree: 
               6:1              
        ┌───────┴───────┐       
       5:1             7:1      
                        └───┐   
                           8:0  

        Expected tree after removing 8:
               6:1      
            ┌───┴───┐   
           5:1     7:1  
        */
        tree.insert(6);
        tree.insert(7);
        tree.insert(5);
        tree.insert(8);
        assertEquals(8, tree.remove(8));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(6, tree.root.data);
        assertEquals(1, tree.root.blackHeight);
        assertEquals(7, tree.root.rightChild.data);
        assertEquals(6, tree.root.rightChild.parent.data);
        assertEquals(1, tree.root.rightChild.blackHeight);
        assertEquals(5, tree.root.leftChild.data);
        assertEquals(6, tree.root.leftChild.parent.data);
        assertEquals(1, tree.root.leftChild.blackHeight);

        //Case 1: removing node with one child (meaning node is black and child is red)
        ////////////////////////////////////////////////////////////////////////////////////
        /*
        Original Tree:
               5:1              
        ┌───────┴───────┐       
       4:1             6:1      
                        └───┐   
                           7:0  
        Expected tree after removing 6
        */
        tree = new RedBlackTree<Integer>();
        tree.insert(5);
        tree.insert(4);
        tree.insert(6);
        tree.insert(7);
        assertEquals(6, tree.remove(6));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(5, tree.root.data);
        assertEquals(1, tree.root.blackHeight);
        assertEquals(4, tree.root.leftChild.data);
        assertEquals(5, tree.root.leftChild.parent.data);
        assertEquals(1, tree.root.leftChild.blackHeight);
        assertEquals(7, tree.root.rightChild.data);
        assertEquals(5, tree.root.rightChild.parent.data);
        assertEquals(1, tree.root.rightChild.blackHeight);
        

        //Case 2: removing node with two children (Whitebox, should replace node's data with succerssor's data, 
        //then recursivly delete sucessor, which should be Case 0)
        ////////////////////////////////////////////////////////////////////////////////////
        /*
        Original tree:
               5:1              
        ┌───────┴───────┐       
       4:1             7:1      
                    ┌───┴───┐   
                   6:0     8:0  
        Expected tree after removing 7:
               5:1              
        ┌───────┴───────┐       
       4:1             8:1      
                    ┌───┘       
                   6:0 
        */
        tree = new RedBlackTree<Integer>();
        tree.insert(5);
        tree.insert(7);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);

        assertEquals(7, tree.remove(7));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(5, tree.root.data);
        assertEquals(1, tree.root.blackHeight);
        assertEquals(4, tree.root.leftChild.data);
        assertEquals(5, tree.root.leftChild.parent.data);
        assertEquals(1, tree.root.leftChild.blackHeight);
        assertEquals(8, tree.root.rightChild.data);
        assertEquals(5, tree.root.rightChild.parent.data);
        assertEquals(1, tree.root.rightChild.blackHeight);
        assertEquals(6, tree.root.rightChild.leftChild.data);
        assertEquals(8, tree.root.rightChild.leftChild.parent.data);
        assertEquals(0, tree.root.rightChild.leftChild.blackHeight);

        //Case 3: Node is not in non-empty tree, should return null
        tree = new RedBlackTree<Integer>();
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);
        assertEquals(null, tree.remove(1));
    }

    /**
     * Tests edge cases of the remove method of the RedBlackTree class that don't involve double blacks
     */
    @Test
    public void test3(){
        //Case 0: node is root with no children, should just set the root to null
        tree.insert(5); 
        assertEquals(5, tree.remove(5));
        assertEquals(null, tree.root);

        //Case 1: node is root with one child, should set root to the child
        ////////////////////////////////////////////////////////////////////////////////////
        tree = new RedBlackTree<Integer>();
        tree.insert(5);
        tree.insert(6);
        assertEquals(6, tree.remove(6));
        assertEquals(5, tree.root.data);
        assertEquals(1, tree.root.blackHeight);

        //Case 2: node is root with 2 children, should replace root's data with the successor, then recursivly remove the successor
        ////////////////////////////////////////////////////////////////////////////////////
        tree = new RedBlackTree<Integer>();
        tree.insert(5);
        tree.insert(6);
        tree.insert(4);
        assertEquals(5, tree.remove(5));
        assertEquals(6, tree.root.data);
        assertEquals(1, tree.root.blackHeight);
        assertEquals(4, tree.root.leftChild.data);
        assertEquals(6, tree.root.leftChild.parent.data);
        assertEquals(0, tree.root.leftChild.blackHeight);

        //Case 3: tree has no elements, should return null
        ////////////////////////////////////////////////////////////////////////////////////
        tree = new RedBlackTree<Integer>();
        assertEquals(null, tree.remove(5));

        //Case 4: null passed into remove, should return null
        ////////////////////////////////////////////////////////////////////////////////////
        assertEquals(null, tree.remove(null));
    }

    /**
     * Tests non-recursive cases for resolving a double black node on the remove method of the RedBlackTree class
     */
    @Test
    public void test4(){
        //trees in this method are not valid RedBlackTrees, but do correctly test functionality

        //Case 0: double black's sibling is black with one or more red children
        ////////////////////////////////////////////////////////////////////////////////////
        //Case 0.1: sibling's only red child is a child of the same side as sibling

        /*
        Original tree:
               8:1              
        ┌───────┴───────┐       
       7:1            10:1      
                        └───┐   
                          11:0  
        Expected tree after removing 7:
              10:1      
            ┌───┴───┐   
           8:1    11:1
        */
        RedBlackTree.Node<Integer> parent = tree.root = new RedBlackTree.Node<Integer>(8);
        parent.blackHeight = 1;
        RedBlackTree.Node<Integer> toRemove = parent.leftChild = new RedBlackTree.Node<Integer>(7);
        toRemove.blackHeight = 1;
        toRemove.parent = parent;
        RedBlackTree.Node<Integer> sibling = parent.rightChild = new RedBlackTree.Node<Integer>(10);
        sibling.blackHeight = 1;
        sibling.parent = parent;
        RedBlackTree.Node<Integer> nephew = sibling.rightChild = new RedBlackTree.Node<Integer>(11);
        nephew.parent = sibling;
        
        assertEquals(7, tree.remove(7));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(false, tree.contains(7));
        assertEquals(sibling, tree.root);
        assertEquals(parent, sibling.leftChild);
        assertEquals(sibling, parent.parent);
        assertEquals(nephew, sibling.rightChild);
        assertEquals(sibling, nephew.parent);
        assertEquals(1, sibling.blackHeight);
        assertEquals(1, parent.blackHeight);
        assertEquals(1, nephew.blackHeight);

        //Case 0.2: sibling's only red child is a child of the opposite side as sibling

        /*
        Original Tree:
               8:1              
        ┌───────┴───────┐       
       7:1            10:1      
                    ┌───┘       
                   9:0   
        Expected tree after removing 7:
               9:1      
            ┌───┴───┐   
           8:1    10:1 
        */
        parent = tree.root = new RedBlackTree.Node<Integer>(8);
        parent.blackHeight = 1;
        toRemove = parent.leftChild = new RedBlackTree.Node<Integer>(7);
        toRemove.parent = parent;
        toRemove.blackHeight = 1;
        sibling = parent.rightChild = new RedBlackTree.Node<Integer>(10);
        sibling.blackHeight = 1;
        sibling.parent = parent;
        nephew = sibling.leftChild = new RedBlackTree.Node<Integer>(9);
        nephew.parent = sibling;

        assertEquals(7, tree.remove(7));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(false, tree.contains(7));
        assertEquals(nephew, tree.root);
        assertEquals(parent, nephew.leftChild);
        assertEquals(nephew, parent.parent);
        assertEquals(sibling, nephew.rightChild);
        assertEquals(nephew, sibling.parent);
        assertEquals(1, sibling.blackHeight);
        assertEquals(1, parent.blackHeight);
        assertEquals(1, nephew.blackHeight);


        //Case 0.3: sibling has 2 red children

        /*
        Original Tree:
               8:1              
        ┌───────┴───────┐       
       7:1            10:1      
                    ┌───┴───┐  
                   9:0     11:0
        Expected tree after removing 7:
             9:1      
        ┌─────┴─────┐   
       8:1         10:1 
        ┴───┐  
           9:0
        */
        parent = tree.root = new RedBlackTree.Node<Integer>(8);
        parent.blackHeight = 1;
        toRemove = parent.leftChild = new RedBlackTree.Node<Integer>(7);
        toRemove.blackHeight = 1;
        toRemove.parent = parent;
        sibling = parent.rightChild = new RedBlackTree.Node<Integer>(10);
        sibling.blackHeight = 1;
        sibling.parent = parent;
        RedBlackTree.Node<Integer> innerNephew = sibling.leftChild = new RedBlackTree.Node<Integer>(9);
        nephew.parent = sibling;
        RedBlackTree.Node<Integer> outerNephew = sibling.rightChild = new RedBlackTree.Node<Integer>(11);
        outerNephew.parent = sibling;

        assertEquals(7, tree.remove(7));
         //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(false, tree.contains(7));
        assertEquals(sibling, tree.root);
        assertEquals(parent, sibling.leftChild);
        assertEquals(sibling, parent.parent);
        assertEquals(outerNephew, sibling.rightChild);
        assertEquals(sibling, outerNephew.parent);
        assertEquals(innerNephew, parent.rightChild);
        assertEquals(parent, innerNephew.parent);
        assertEquals(1, sibling.blackHeight);
        assertEquals(1, parent.blackHeight);
        assertEquals(0, innerNephew.blackHeight);
        assertEquals(1, outerNephew.blackHeight);

        //Case 1: double black's sibling is black with no red children
        //should subtract one from sibling's black height, and add one to parent's black height, then recurse at the parent
        ////////////////////////////////////////////////////////////////////////////////////
        
        //Case 1.1: parent is root, should just change parent's black height from 2 to 1

        //Not going to test this with different numbers of children because I know my implementation isn't affected by that
        //Just testing with 2 children

        /*
        Origninal Tree:
               8:1              
        ┌───────┴───────┐       
       7:1            10:1      
                    ┌───┴───┐   
                   9:1    11:1  
        Expected Tree after removing 7:
               8:1              
                └───────┐       
                      10:0      
                    ┌───┴───┐   
                   9:1    11:1  
        */
        parent = tree.root = new RedBlackTree.Node<Integer>(8);
        parent.blackHeight = 1;
        toRemove = parent.leftChild = new RedBlackTree.Node<Integer>(7);
        toRemove.blackHeight = 1;
        toRemove.parent = parent;
        sibling = parent.rightChild = new RedBlackTree.Node<Integer>(10);
        sibling.blackHeight = 1;
        sibling.parent = parent;
        innerNephew = sibling.leftChild = new RedBlackTree.Node<Integer>(9);
        innerNephew.parent = sibling;
        innerNephew.blackHeight = 1;
        outerNephew = sibling.rightChild = new RedBlackTree.Node<Integer>(11);
        outerNephew.parent = sibling;
        outerNephew.blackHeight = 1;

        assertEquals(7, tree.remove(7));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(false, tree.contains(7));
        assertEquals(parent, tree.root);
        assertEquals(sibling, parent.rightChild);
        assertEquals(parent, sibling.parent);
        assertEquals(innerNephew, sibling.leftChild);
        assertEquals(sibling, innerNephew.parent);
        assertEquals(outerNephew, sibling.rightChild);
        assertEquals(sibling, outerNephew.parent);
        assertEquals(1, parent.blackHeight);
        assertEquals(0, sibling.blackHeight);
        assertEquals(1, sibling.leftChild.blackHeight);
        assertEquals(1, sibling.rightChild.blackHeight);

        //Case 1.2: parent is red, meaning parent's black height just ends as 1 and there is no new double black to resolve

        /*
        Original Tree:
                               6:1                              
                                └───────────────┐               
                                               8:0              
                                        ┌───────┴───────┐       
                                       7:1            10:1      
                                                    ┌───┴───┐   
                                                   9:1    11:1  
        Expected tree after removing 7:
                               6:1                              
                                └───────────────┐               
                                               8:1              
                                                └───────┐       
                                                      10:0      
                                                    ┌───┴───┐   
                                                   9:1    11:1  
        */
        tree.root = new RedBlackTree.Node<Integer>(6);
        tree.root.blackHeight = 1;
        parent = tree.root.rightChild = new RedBlackTree.Node<Integer>(8);
        toRemove = parent.leftChild = new RedBlackTree.Node<Integer>(7);
        toRemove.blackHeight = 1;
        toRemove.parent = parent;
        sibling = parent.rightChild = new RedBlackTree.Node<Integer>(10);
        sibling.blackHeight = 1;
        sibling.parent = parent;
        innerNephew = sibling.leftChild = new RedBlackTree.Node<Integer>(9);
        innerNephew.parent = sibling;
        innerNephew.blackHeight = 1;
        outerNephew = sibling.rightChild = new RedBlackTree.Node<Integer>(11);
        outerNephew.parent = sibling;
        outerNephew.blackHeight = 1;

        assertEquals(7, tree.remove(7));
        //verify the tree matches the above, including parent and child references, as well as black heights
        assertEquals(false, tree.contains(7));
        assertEquals(parent, tree.root.rightChild);
        assertEquals(sibling, parent.rightChild);
        assertEquals(parent, sibling.parent);
        assertEquals(innerNephew, sibling.leftChild);
        assertEquals(sibling, innerNephew.parent);
        assertEquals(outerNephew, sibling.rightChild);
        assertEquals(sibling, outerNephew.parent);
        assertEquals(1, parent.blackHeight);
        assertEquals(0, sibling.blackHeight);
        assertEquals(1, sibling.leftChild.blackHeight);
        assertEquals(1, sibling.rightChild.blackHeight);

    }

    /**
     * Tests recursive double black situations for the remove method of the RedBlackTree class
     */
    @Test
    public void test5(){
        //Case 0: Same as Case 1 from test4, but parent is black and is not root
        ///////////////////////////////////////////////////////////////////////////////////

        /*
        Original Tree:
                               5:1                              
                ┌───────────────┴───────────────┐               
               4:1                             7:1              
        ┌───────┘                       ┌───────┴───────┐       
       3:0                             6:1             9:1      
                                                    ┌───┴───┐   
                                                   8:1    10:1 
        Tree after resolving double black at 6:
                               5:1                              
                ┌───────────────┴───────────────┐               
               4:1                             7:2              
        ┌───────┘                       ┌───────┴───────┐       
       3:0                             6:1             9:0     
                                                    ┌───┴───┐   
                                                   8:1    10:1     
        Tree after resolving double black at 7 and deleting 6:
                                                               4:1                                                              
                                ┌───────────────────────────────┴───────────────────────────────┐                               
                               3:1                                                             5:1                              
                                                                                                └───────────────┐               
                                                                                                               7:1              
                                                                                                                └───────┐       
                                                                                                                       9:0      
                                                                                                                    ┌───┴───┐   
                                                                                                                   8:1    10:1 
        */
        tree.insert(5);
        tree.insert(4);
        tree.insert(7);
        tree.insert(3);
        tree.insert(6);
        tree.insert(9);
        tree.insert(8);
        tree.insert(10);
        tree.root.rightChild.blackHeight = 1;
        tree.root.rightChild.rightChild.leftChild.blackHeight = 1;
        tree.root.rightChild.rightChild.rightChild.blackHeight = 1;


        assertEquals(6, tree.remove(6));
        //only going to check a few spots since this is a very large tree
        assertEquals(false, tree.contains(6));
        assertEquals(4, tree.root.data);
        assertEquals(1, tree.root.blackHeight);
        assertEquals(3, tree.root.leftChild.data);
        assertEquals(1, tree.root.leftChild.blackHeight);
        assertEquals(9, tree.root.rightChild.rightChild.rightChild.data);
        assertEquals(0, tree.root.rightChild.rightChild.rightChild.blackHeight);


        //Case 1: double black's sibling is red
        ///////////////////////////////////////////////////////////////////////////////////

        //Case I will use to test this will end up in test4's Case 1.2 after recursing, as shown below
        /*
        Original Tree:
               5:1              
        ┌───────┴───────┐       
       4:1             7:0      
                    ┌───┴───┐   
                   6:1     8:1 
        Tree after first layer of recursion:
                    7:1              
             ┌───────┴───────┐       
            5:1             8:1      
         ┌───┴───┐   
        4:2     6:1
        Tree after resolving double black fully and deleting 4:
               7:1              
        ┌───────┴───────┐       
       5:1             8:1      
        └───┐                   
           6:0

        */
        tree = new RedBlackTree<Integer>();
        tree.insert(5);
        tree.insert(4);
        tree.insert(7);
        tree.insert(6);
        tree.insert(8);
        tree.root.rightChild.blackHeight = 0;
        tree.root.rightChild.leftChild.blackHeight = 1;
        tree.root.rightChild.rightChild.blackHeight = 1;

        assertEquals(4, tree.remove(4));
        //check to make sure child references, parent references, and black heights match the above
        assertEquals(7, tree.root.data);
        assertEquals(5, tree.root.leftChild.data);
        assertEquals(7, tree.root.leftChild.parent.data);
        assertEquals(6, tree.root.leftChild.rightChild.data);
        assertEquals(5, tree.root.leftChild.rightChild.parent.data);
        assertEquals(8, tree.root.rightChild.data);
        assertEquals(7, tree.root.rightChild.parent.data);
        assertEquals(1, tree.root.blackHeight);
        assertEquals(1, tree.root.leftChild.blackHeight);
        assertEquals(1, tree.root.rightChild.blackHeight);
        assertEquals(0, tree.root.leftChild.rightChild.blackHeight);
    }
}
