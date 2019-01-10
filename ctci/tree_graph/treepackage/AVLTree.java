/**
AVL tree is a self-balancing Binary Search Tree (BST) where the difference between heights of left and right subtrees cannot be more than one for all nodes.
*/

/**
AVL Node:
AVL nodes have val and height. It uses height to determine if balanced.

Balance Factor:
Compare LeftHeight to RightHeight, LeftHeight - RightHeight = 1, 0, or -1 then is balanced
LeftHeight - RightHeight >= 2 then leftBranch is higher by 2 or more, unbalance
LeftHeight - RightHeight <= -2 then rightBranch is higher by 2 or more, unbalance
Perform rotation according to left or right is unbalance.

Rotations:
a) Left Left Case

T1, T2, T3 and T4 are subtrees.
         z                                      y 
        / \                                   /   \
       y   T4      Right Rotate (z)          x      z
      / \          - - - - - - - - ->      /  \    /  \ 
     x   T3                               T1  T2  T3  T4
    / \
  T1   T2

b) Left Right Case

     z                               z                           x
    / \                            /   \                        /  \ 
   y   T4  Left Rotate (y)        x    T4  Right Rotate(z)    y      z
  / \      - - - - - - - - ->    /  \      - - - - - - - ->  / \    / \
T1   x                          y    T3                    T1  T2 T3  T4
    / \                        / \
  T2   T3                    T1   T2

c) Right Right Case

  z                                y
 /  \                            /   \ 
T1   y     Left Rotate(z)       z      x
    /  \   - - - - - - - ->    / \    / \
   T2   x                     T1  T2 T3  T4
       / \
     T3  T4

d) Right Left Case

   z                            z                            x
  / \                          / \                          /  \ 
T1   y   Right Rotate (y)    T1   x      Left Rotate(z)   z      y
    / \  - - - - - - - - ->     /  \   - - - - - - - ->  / \    / \
   x   T4                      T2   y                  T1  T2  T3  T4
  / \                              /  \
T2   T3                           T3   T4

Insertion:
After insert a node, one side could be unbalance by 2. Check balanceFactor 2 or -2;
(don't forget to update height of root)
If Diff >= 2 indicate left > right by 2. So left side is too long
	* Then we check if is Left Left or Left Right, 
	* if Left Right, val < root && val > root.left, rotate left on root.left to become Left Left first.
	* then right rotate on root to become balanced
	* if Left Left, inserted val < root && val < root.left.
	* just right rotate on root to become balanced
If Diff <= -2 indicates right > left by 2. So right side is too long.
	* We check if is Right Right or Right Left
	* if Right Left, val > root && val < root.right, rotate right on root.right to form Right Right
	* Then then left rotate on root to balance
	* if Right Right, val > root && val > root.right
	* Just left rotate on root to balance

Deletion:
First, perform regular BST deletion: 3 cases.
Case 1: target is leaf, just return null for parent->null;
Case 2: target has only 1 child, return child (left/right), parent->child
Case 3: target have 2 children left and right, 
	* find smallest node on target.right, assign target.right = minNode.Val.
	* Then delete minNode on right.
After deletion:
if no more node on tree, just return null.
if there is still nodes:
Update the height of root.
Get the balanceFactor:
Now depending on the balanceFactor >= 2 or <= -2 we do the same rotation as Insertion, with only 1 differentce.
Difference: when root.left is heavy by 2, if root.left's left is heavier balanceFactor >= 1 or root.left's left and right ==, balanceFactor == 0; We will just perform Left Left case right rotate on root.
Same with Right Right case.
*/

// package treepackage;
import java.io.*;
import java.util.*;

public class AVLTree {

	class Node {
		int val;
		int height;
		Node left;
		Node right;

		Node(int val) {
			this.val = val;
			this.height = 1;
			left = right = null;
		}
	}

	Node root = null;

	public int height(Node node) {
		if (node == null) {
			return 0;
		}
		return node.height;
	}

	// 2/1 is leftH > rightH by 2/1, -2/-1 is rightH > leftH by 2/1.
	public int balanceFactor(Node node) {
		// check the height diff of left and right subtree
		if (node == null) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	/**
	 * Time: O(1)
	 */
	public Node leftRotate(Node target) {
		// target is the root to rotate.
		// target's right node to become root.
		Node rightNode = target.right;
		// rightNode's left node to connect as target.right.
		Node cutOffNode = rightNode.left;
		// Rotate left.
		// rightNode's left becomes target, as rightNode becomes root.
		rightNode.left = target;
		// target's right node becomes rightNode's left node.
		target.right = cutOffNode;

		// update heights, target update first b/c it is a child of rightNode now.
		target.height = 1 + Math.max(height(target.left), height(target.right));
		rightNode.height = 1 + Math.max(height(rightNode.left), height(rightNode.right));
		// return new root.
		return rightNode;
	}

	/**
	 * Time: O(1)
	 */
	public Node rightRotate(Node target) {
		// target is the root to rotate.
		// target's leftNode becomes new root.
		Node leftNode = target.left;
		// cut off leftNode's right node to connect to target's left.
		Node cutOffNode = leftNode.right;
		// Rotate right
		// leftNode connect target as right.
		leftNode.right = target;
		target.left = cutOffNode;

		// update heights for switched target and leftNode, other children Nodes remain the same height.
		target.height = 1 + Math.max(height(target.left), height(target.right));
		leftNode.height = 1 + Math.max(height(leftNode.left), height(leftNode.right));
		// return new root.
		return leftNode;
	}

	/**
	 * Time: O(lgN), find the node go down the branch. Height of the tree.
	 * B/c the tree is always rebalanced, the insertion takes O(lgN) or O(h) always;
	 */
	public Node insert(Node rootNode, int val) {
		// find where the val lies
		if (rootNode == null) {
			return new Node(val);
		}
		if (rootNode.val > val) {
			// on left;
			rootNode.left = insert(rootNode.left, val);
		} else if (rootNode.val < val) {
			rootNode.right = insert(rootNode.right, val);
		} else {
			// same val, duplicates not allow in this example.
			return rootNode;
		}
		// update height
		rootNode.height = 1 + Math.max(height(rootNode.left), height(rootNode.right));

		// check balanced.
		int diff = balanceFactor(rootNode);
		// 4 cases:
		// Diff >= 2 indicate left > right by 2. So left side is too long
		// Left Left, inserted val < root && val < root.left. 
		if (diff >= 2 && val < rootNode.left.val) {
			// rotate right once on root to get balance
			return rightRotate(rootNode);
		}
		// Left Right, val < root && val > root.left
		if (diff >= 2 && val > rootNode.left.val) {
			// rotate left on root.left to become Left Left first.
			// newRoot is returned, connect to rootNode
			rootNode.left = leftRotate(rootNode.left);
			// then right rotate on root to become balanced
			return rightRotate(rootNode);
		}
		// Diff <= -2 indicates right > left by 2. So right side is too long.
		// Right Right, val > root && val > root.right
		if (diff <= -2 && val > rootNode.right.val) {
			// rotate left once on root to get balance.
			return leftRotate(rootNode);
		}
		// Right Left, val > root && val < root.right
		if (diff <= -2 && val < rootNode.right.val) {
			// rotate right to become Right Right, connect root to newRight
			rootNode.right = rightRotate(rootNode.right);
			// then left rotate to balance
			return leftRotate(rootNode);
		}
		return rootNode;
	}

	public Node minNode(Node rootNode) {
		if (rootNode == null) {
			return null;
		}
		// min is the left most node
		while (rootNode.left != null) {
			rootNode = rootNode.left;
		}
		return rootNode;
	}

	/**
	 * Time: O(lgN)
	 */
	public Node delete(Node rootNode, int val) {
		// Do regular BST delete first
		// nothing to delete
		if (rootNode == null) {
			return null;
		}
		// search node
		if (rootNode.val > val) {
			// on left
			rootNode.left = delete(rootNode.left, val);
		} else if (rootNode.val < val) {
			// on right
			rootNode.right = delete(rootNode.right, val);
		} else {
			// if rootNode is == val, delete this node.
			// 3 Cases
			// Case1: rootNode is leaf, then no child need to connect
			if (rootNode.left == null && rootNode.right == null) {
				rootNode = null;
			} else if (rootNode.left != null || rootNode.right != null) {
				// Case2: rootNode has only 1 child left or right, return that child as new root.
				if (rootNode.left == null) {
					rootNode = rootNode.right;
				} else if (rootNode.right == null) {
					rootNode = rootNode.left;
				} else {
					// Case3: both left and right have nodes
					// search the smallest node on right.
					Node minNode = minNode(rootNode.right);
					// replace root with minNode's val
					rootNode.val = minNode.val;
					// delete minNode.
					rootNode.right = delete(rootNode.right, minNode.val);
				}
			} 
		}
		// finished regular BST deletion.
		// only 1 node, then return
		if (rootNode == null) {
			return null;
		}
		// update the height of rootNode
		rootNode.height = 1 + Math.max(height(rootNode.left), height(rootNode.right));
		// Check balance factor
		int diff = balanceFactor(rootNode);
		// 4 cases
		// Left heavy diff >= 2
		// Left Left, deleted val on right and leftside's leftBranch is heavier/highest.
		// or leftside's left and right ==
		if (diff >= 2 && balanceFactor(rootNode.left) >= 0) {
			// rotate right the entire tree to balance
			return rightRotate(rootNode);
		}
		// Left Right, deleted val on right and leftside's rightBranch is heavier/highest
		if (diff >= 2 && balanceFactor(rootNode.left) <= -1) {
			// rotate left on root.left to form Left Left shape
			rootNode.left = leftRotate(rootNode.left);
			// then rotate right the entire tree to balance
			return rightRotate(rootNode);
		}
		// Right side heavy, <= -2
		// Right Right, deleted val on left and rightside's rightBranch is heavier/highest
		// or rightside's left and right ==
		if (diff <= -2 && balanceFactor(rootNode.right) <= 0) {
			// rotate left on entire tree to balance
			return leftRotate(rootNode);
		}
		// Right Left, deleted val on left and rightside's leftBranch is heavier/highest
		if (diff <= -2 && balanceFactor(rootNode.right) >= 1) {
			// rotate right on root.right to form Right Right
			rootNode.right = rightRotate(rootNode.right);
			// then rotate left on entire tree to balance
			return leftRotate(rootNode);
		}
		// return delete/updated tree
		return rootNode;
	}


	// print preorder
	public void preOrder(Node node) { 
    if (node == null) { 
    	return;
    }
    System.out.print(node.val + " "); 
    preOrder(node.left);
    preOrder(node.right); 
  } 

  public static void main(String[] args) { 
    AVLTree tree = new AVLTree(); 

    /* Constructing tree given in the above figure */
    tree.root = tree.insert(tree.root, 10); 
    tree.root = tree.insert(tree.root, 20); 
    tree.root = tree.insert(tree.root, 30); 
    tree.root = tree.insert(tree.root, 40); 
    tree.root = tree.insert(tree.root, 50); 
    // tree.root = tree.insert(tree.root, 25);
    // tree.root = tree.insert(tree.root, 5);
    // tree.root = tree.insert(tree.root, 23);
    tree.root = tree.insert(tree.root, 35);
    tree.root = tree.insert(tree.root, 60);


    /* The constructed AVL Tree would be 
         30 
        /  \ 
      20   40 
     /  \     \ 
    10  25    50 
    /		/
   (5) (23)
    */
    System.out.println("Preorder traversal of constructed tree is : "); 
    tree.preOrder(tree.root); 
    System.out.println();

    System.out.println("delete Node 20 to: "); 
    tree.root = tree.delete(tree.root, 20);
    tree.preOrder(tree.root); 

    System.out.println("delete Node 10 to: "); 
    tree.root = tree.delete(tree.root, 10);
    tree.preOrder(tree.root); 
    System.out.println();
  }

}