/**
66. Binary Tree Preorder Traversal
Given a binary tree, return the preorder traversal of its nodes' values.

Example
Given:

    1
   / \
  2   3
 / \
4   5
return [1,2,4,5,3].

Challenge
Can you do it without recursion?
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class BinaryTreePreorder {
    /**
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     * When print, just print. When visit, becomes 3 operations.
     * [1,2,3,4,5] => [1,2,4,5,3] root, left, right
	* How to arrive the answer:
	*
	* Realize there are 2 posible operations: print or visit;
	* When visit, it becomes 3 calls -> print self, visit left, visit right;
	* PROBLEM: what data structure to use that will allows me to print 4 first, despite 3 being in the data first.
	* KEY: use a Stack. LIFO. When I visit node 2, the stack is [v2->push(p2, v4, v5), v3];
	* Ex: I can push 4,5 to be pop first, despite node 3 being there first.
	* 
	* Time: O(N), visit nodes once.
	* Space: O(N);
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        Stack<Guide> stack = new Stack();

        stack.push(new Guide(root, 0)); //visit root
        while(!stack.empty())
        {
            Guide curGuide = stack.pop(); //LIFO, remove top.
            //root.left or right is null, go to next node on stack
            if(curGuide.node == null) continue;
            
            if(curGuide.ope == 1)
            {
                res.add(curGuide.node.val);
            }
            else //visit
            {
                stack.push(new Guide(curGuide.node.right, 0)); //this pop last
                stack.push(new Guide(curGuide.node.left, 0)); //this pop second
                stack.push(new Guide(curGuide.node, 1)); //this first
            }
        }
        return res;
    }
    
    class Guide
    {
        public int ope; //0 visit, 1 print
        public TreeNode node;
        public Guide(TreeNode node, int ope)
        {
            this.ope = ope;
            this.node = node;
        }
    }

    public static void main(String[] args) {
    	BinaryTreePreorder obj = new BinaryTreePreorder();
    	TreeNode node1 = new TreeNode(1);
    	TreeNode node2 = new TreeNode(2);
    	TreeNode node3 = new TreeNode(3);
    	TreeNode node4 = new TreeNode(4);
    	TreeNode node5 = new TreeNode(5);

    	node1.left = node2;
    	node1.right = node3;
    	node2.left = node4;
    	node2.right = node5;
    	List<Integer> res = obj.preorderTraversal(node1);
    	System.out.println(Arrays.toString(res.toArray()));
    }
}