/** BinarySearchTree.java
 * For Lab8Assign5 Bonus Part.
 *  Added delete2(E) method, which replace item to delete by its in order successor
 *     when the item to delete has two children.
 * Scroll down toward the end of the file to find delete2(E) method.
 */
package lab8assign5_bonus_template;

import java.util.List;
import java.util.ArrayList;

/**
 * A class to represent a binary search tree.
 * The parent class of BinarySearchTree is the BinaryTree class.
 * 
 * @author Koffman and Wolfgang
 */
/* CIS 2168 Data Structures
 *  Section Number: 003
 *  Colden Jeanmonod tur26337@temple.edu
 *  Assignment Name: Assign 5
 *  Class Name: BinarySearchTree
 * Runs through various methods in the binary search class
 */
    //BinarySearchTree is a subclass of BinaryTree and implements the interface SearchTree.
    //The element class type E must implement the interface Comparable.
public class BinarySearchTree<E extends Comparable<E>>
         extends BinaryTree<E>
        implements SearchTree<E> {
     
    
    // Data Fields: only used for self-balancing
    /** Return value from the public add method, indicating whether the item was inserted or not. */
    protected boolean addReturn;
    /** Return value from the public delete method, which is a reference to the deleted item. */
    protected E deleteReturn;

    
    //Constructors (not included in the textbook code)
    
    /**no-argument constructor
     * for the client class to create an empty BinarySearchTree
     */
    public BinarySearchTree(){
        super();    //call BinaryTree()
    } 
    
    /**
     * for the client class to create a single-item BinarySearchTree
     * @param data - the given data item in the root node of the new tree
     */
    public BinarySearchTree(E data){
        super(data);    //call BinaryTree(E data)
    } 
    
    /**
     * for the client class to create a BinarySearchTree with the given 
     *  root data, given left subtree, given right subtree
     * @param data - given root data item
     * @param leftSubTree - given left subtree
     * @param rightSubTree - given right subtree
     */

    public BinarySearchTree(E data, BinarySearchTree<E> leftSubTree, 
            BinarySearchTree<E> rightSubTree){
        //call BinaryTree(E data, BinaryTree<E> leftSubtree, BinaryTree<E> rightSubtree)
        super(data, leftSubTree, rightSubTree);
    }      
    
    //Methods
    /*<listing chapter="6" number="4">*/
    /**
     * Starter method find.
     * @pre The target object must implement the Comparable interface.
     * @param target The Comparable object being sought
     * @return The object, if found, otherwise null
     */
    @Override
    public E find(E target) {
               //call the private recursive version to do the actual search,
               //  pasing the original root of this BST as the local root
        return find(root, target);
    }

    /**
     * Recursive find method.
     * @param localRoot The local subtree's root (i.e. current root node)
     * @param target The object being sought
     * @return The object, if found, otherwise null
     */
    private E find(Node<E> localRoot, E target) {
        if (localRoot == null) {   //base case 1: target is not in this BST tree
            return null;
        }
        
        // Compare the target with the data at the current root node).
        int compResult = target.compareTo(localRoot.data);
        if (compResult == 0) {     //base case 2: found the target in current root node
            return localRoot.data;
        } else if (compResult < 0) {                //target is smaller than the current local root data
            return find(localRoot.left, target);    //recursively search the left subtree of the current root
        } else {                                    //target is larger than the current local root data
            return find(localRoot.right, target);   //recursively search the right subtree of the current root
        }
    }
    /*</listing>*/

    /*<listing chapter="6" number="5">*/
    /**
     * Starter method add.
     * @pre The object to insert must implement the Comparable interface.
     * @param item The object being inserted
     * @return true if the object is inserted, false
     *         if the object already exists in the tree
     */
    @Override
    public boolean add(E item) {        
        //special case: empty tree can be handled here or inside the private recursive add(..)
        if (root == null) {
            root = new Node<E>(item);
            addReturn = true;
            return true;
        }
        
        //call the private recursive version to do the actual addition,
        //  pasing the original root of this BST as the local root,
        //assign the return value of the private add method to this BST's root.        
        root = add(root, item);  
        return addReturn;
    }

    /**
     * Recursive add method.
     * @post The data field addReturn is set true if the item is added to
     *       the tree, false if the item is already in the tree.
     * @param localRoot The local root of the subtree (i.e. the current root)
     * @param item The object to be inserted
     * @return The new local root that now contains the inserted item
     */
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree and insert it.
            addReturn = true;
            return new Node<E>(item);
        } else if (item.compareTo(localRoot.data) == 0) {
            // item is equal to localRoot.data, a duplicate, so cannot insert it
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            // item is less than localRoot.data.
            // Try to recursively add the item to the left subtree of the local root.
            // Don't have to update localRoot.left at every return from private add.
            //  The update is used here mainly for tree balancing in Chapter 9.
            localRoot.left = add(localRoot.left, item);         
            return localRoot;
        } else {
            // item is greater than localRoot.data
            // Try to recursively add the item to the right subtree of the local root.
            // Don't have to update localRoot.right at every return from private add.
            //  The update is used here mainly for tree balancing in Chapter 9.            
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }
    /*</listing>*/

    /*<listing chapter="6" number="6">*/
    /**
     * Starter method delete.
     * @post The object is not in the tree.
     * @param target The object to be deleted
     * @return The object deleted from the tree
     *         or null if the object was not in the tree
     * @throws ClassCastException if target does not implement Comparable
     */
    public E delete(E target) {
        root = delete(root, target);
        return deleteReturn;
    }

    /**
     * Recursive delete method.
     * @post The item is not in the tree;
     *       deleteReturn is equal to the deleted item
     *       as it was stored in the tree or null
     *       if the item was not found.
     * @param localRoot The root of the current subtree
     * @param item The item to be deleted
     * @return The modified local root that does not contain
     *         the item
     */
    private Node<E> delete(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree.
            deleteReturn = null;
            return localRoot;
        }

        // Search for item to delete.
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            localRoot.left = delete(localRoot.left, item);
            return localRoot;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            localRoot.right = delete(localRoot.right, item);
            return localRoot;
        } else {
            // item is at local root.
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {  //0 or 1 child
                // If there is no left child, return right child
                // which can also be null.
                return localRoot.right;
            } else if (localRoot.right == null) { //0 or 1 child
                // If there is no right child, return left child.
                return localRoot.left;
            } else {        //2 children
                // Node being deleted has 2 children, replace the data
                // with inorder predecessor, which is the largest in left subtree.
                if (localRoot.left.right == null) {
                    // The left child has no right child.
                    // Replace the local root data with the data in the
                    // left child.
                    localRoot.data = localRoot.left.data;
                    // Replace the left child with left child's left child.
                    localRoot.left = localRoot.left.left;
                    return localRoot;
                } else {
                    // Search for the inorder predecessor (ip) and
                    // replace deleted node's data with ip.
                    localRoot.data = findLargestChild(localRoot.left);
                    return localRoot;
                }
            }
        }
    }
    /*</listing>*/

    /*<listing chapter="6" number="7">*/
    /**
     * Find the node that is the
     * inorder predecessor and replace it
     * with its left child (if any).
     * @post The inorder predecessor is removed from the tree.
     * @param parent The parent of possible inorder
     *        predecessor (ip)
     * @return The data in the ip
     */
    private E findLargestChild(Node<E> parent) {
        // If the right child has no right child, then the right child is 
        // the inorder predecessor.
        if (parent.right.right == null) {
            //right child is theinorder predecessor
            E returnValue = parent.right.data; 
            //remove the inorder predecessor by replacing it with its left child
            parent.right = parent.right.left;  
            return returnValue;
        } else {
            //keep processing, starting with the right child.
            return findLargestChild(parent.right);
        }
    }
    /*</listing>*/      
    
    
    //========= Lab8Assign5 Bonus Start =======================//  

    
    /**
     * Starter method delete2. delete2 is the same as delete except that when a
     * value to be deleted has two children, the inorder sucessor 
     * (i.e. smallest in right subtree) replaces it in the tree.
     *
     * @post The object is not in the tree.
     * @param target The object to be deleted
     * @return The object deleted from the tree or null if the object was not in
     * the tree
     * @throws ClassCastException if target does not implement Comparable
     */
    public E delete2(E target) {
        root = delete2(root, target);
        return deleteReturn;
    }

    /**
     * Recursive delete2 method.
     *
     * @post The item is not in the tree; deleteReturn is equal to the deleted
     * item as it was stored in the tree or null if the item was not found.
     * private delete2 is the same as private delete except that when a
     * value to be deleted has two children, the inorder sucessor 
     *   (i.e. smallest in right subtree) replaces it in the tree. 
     * @param localRoot The root of the current subtree
     * @param item The item to be deleted
     * @return The modified local root that does not contain the item
     */

    private Node<E> delete2(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree.
            deleteReturn = null;
            return localRoot;
        }

        // Search for item to delete.
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            localRoot.left = delete2(localRoot.left, item);
            return localRoot;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            localRoot.right = delete2(localRoot.right, item);
            return localRoot;
        } else {
            // item to delete is at local root.
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // If there is no left child, return right child
                // which can also be null.
                return localRoot.right;
            } else if (localRoot.right == null) {
                // If there is no right child, return left child.
                return localRoot.left;
            } else {               
                
                //---Lab8Assign5 Bonus Hints ----//                
                
                // localRoot has 2 children, replace the item to delete
                // with its inorder successor (i.e. smallest in right subtree,
                //   also the leftmost in right subtree)
                
                //The code is very similar to what's in the private delete method
                
                //a. handle the easier case: 
                //     data in right child is the smallest in right subtree
                //     (no need to search)
                //b. handle the more general case:
                //     data in right child is not the smallest in right subtree,
                //     need to call findSmallestChild to locate this item,
                //         remove this item, and return this item (minInTR).
                //     then replace the data in localRoot by this item: minInTR.
                
                //Note: following line is a stub used to silent compiler. 
                //   You need to change it when you complete your work.

                E minInTR = findSmallestChild(localRoot.right);

                // Replace the data in localRoot with the inorder successor.
                localRoot.data = minInTR;

                // Remove the inorder successor from the right subtree.
                localRoot.right = delete2(localRoot.right, minInTR);

                return localRoot;

            }
        }
    }
    
    /**
     * Find the smallest item (leftmost item)
     *      in the given BST rooted at given root: parent,
     *    remove this smallest item, and return this smallest item.
     *
     * @post The smallest item in the given BST rooted at given root: parent is removed.
     * @param parent The parent of 
     * @return The smallest item in the given BST rooted at given root: parent
     */
    private E findSmallestChild(Node<E> parent) {
        
        //-----Lab8Assign5 Bonus HINT ------------//
        
        // code is very similar to findLargestChild
        
        // keep following left branch until reaching the parent of leftmost node
        //   then save this item, remove this item, and return this item.
        
        //Note: following line is a stub used to silent compiler. 
        //   You need to change it when you complete your work.
        //while loop to search through the Binary Tree
        while (parent.left != null) {
            parent = parent.left;
        }
        return parent.data;

    }

    //========= Lab8Assign5 Bonus End =======================//    
    
           

    //Other methods in the interface SearchTree
    //   to be implemented by Students.
    
    /** 
     * Removes target from tree.
     * @param target Item to be removed
     * @return true if the object was in the tree, false otherwise
     * @post target is not in the tree
     */
    public boolean remove(E target) {
        //to be implemented by Students
        return false;
    }

    /**
     * Determine if an item is in the tree
     * @param target Item being sought in tree
     * @return true If the item is in the tree, false otherwise
     */
    public boolean contains(E target) {
        //to be implemented by Students
        return false;
    }

}
/*</listing>*/
