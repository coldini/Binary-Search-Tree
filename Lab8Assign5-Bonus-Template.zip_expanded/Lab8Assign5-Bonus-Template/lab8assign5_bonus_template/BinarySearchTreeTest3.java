/** BinarySearchTreeTest3.java
* For Lab8Assign5 Bonus Part.
*   It creates a BST based on an integer array.
*   Then delete 24 using delete2(E), 
*    if the item to delete has two children, 
*         replace it by its in order successor.
*   Display the BST before and after the deletion.
* You don’t need to change this file: BinarySearchTreeTest3.java.
*/

package lab8assign5_bonus_template;
/* CIS 2168 Data Structures
 *  Section Number: 003
 *  Colden Jeanmonod tur26337@temple.edu
 *  Assignment Name: Assign 5
 *  Class Name: Binary Search Tree
 * Runs through already implemented binary tree things to print and delete them
 */
public class BinarySearchTreeTest3 {

    public static void main(String[] args) {
        //create an empty binary search tree
        BinarySearchTree<Integer> bstNumbers = new BinarySearchTree<Integer>();
        int[] numbers = {41,  50,  24,  37,  13,  84, 27, 34};  
        //populate the binary search tree        
        for (int i = 0; i < numbers.length; i++) {
            bstNumbers.add(numbers[i]);            
        }
        
        System.out.println("---------------- -----");
        System.out.println("Before deleting 24 using delete2(E)");
        //display the data and structure of the binary search tree before the deletion
        System.out.println(bstNumbers);
        
        System.out.println("---------------------");
        //delete a number using delete2 method
        bstNumbers.delete2(24);
        
        System.out.println("After deleting 24 using delete2(E)");
        //display the data and structure of the binary search tree after the deletion        
        System.out.println(bstNumbers);
        System.out.println("---------------------");
    }        
}

/* Program Output
---------------------
Before deleting 24 using delete2(E)
41
  24
    13
      null
      null
    37
      27
        null
        34
          null
          null
      null
  50
    null
    84
      null
      null

---------------------
After deleting 24 using delete2(E)
41
  27
    13
      null
      null
    37
      34
        null
        null
      null
  50
    null
    84
      null
      null

--------------------- 
 */