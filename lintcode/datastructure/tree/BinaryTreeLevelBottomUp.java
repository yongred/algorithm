/**
70. Binary Tree Level Order Traversal II
Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).

Example
Given binary tree {3,9,20,#,#,15,7},

    3
   / \
  9  20
    /  \
   15   7
 

return its bottom-up level order traversal as:

[
  [15,7],
  [9,20],
  [3]
]
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class BinaryTreeLevelBottomUp {

	/**
     * @param root: A tree
     * @return: buttom-up level order a list of lists of integer
     Solution 1:
How I arrive:

Bottom up is the same as Top down, except reversed. So we just need to reverse the result list.
Find the Top-Down by using Queue
Adding cur level nodes to Queue, and find Level node # by counting queue size.
Loop Level Count # of times, So we know when cur level ended.
Adding cur level nodes' children into Queue (next level); then pop out cur level nodes from Queue.
Add cur level nodes to SubList. Add subList to Res List.
Time: O(n); Space: O(N);
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();

    	if(root == null) 
    		return res;

    	queue.add(root);
    	while(!queue.isEmpty()){
    		//queue only have cur level elements left; since all upper level is traversed and removed.
    		int levelwidth = queue.size();
    		List<Integer> sublist = new LinkedList<Integer>();
    		//traverse and remove all elements in this level
    		for (int i=0; i<levelwidth; i++){
    			//add children for next level traverse
    			if(queue.peek().left != null)
    				queue.add(queue.peek().left);
    			if(queue.peek().right != null) 
    				queue.add(queue.peek().right);
    			//remove cur level nodes and add to sublist
    			sublist.add(queue.poll().val);
    		}
    		//add this level sublist to res
    		res.add(sublist);
    	}
        Collections.reverse(res);
    	return res;
    }

    /**
     * @param root: A tree
     * @return: buttom-up level order a list of lists of integer
     * Solution 2:
	* How I arrive:
	* What if we know the Height? (how many levels)
	* Then we can Know, when Height -1 == 2nd level, Height - (height-1) == last level == 1; 
	* Then each time Node.left or Node.left is Level-1.
	* Then we can work from bottom up. Last level == 1, Height - (height-2), Height - (height-3).
	* Use a recursion to goes down until level == 1;
	* create LevelList nodes from each level.
	*
	* Space: O(N) from recursive function. 
	* Time: O(n^2);
	* T(n) + T(n- lastLv#) + T(n- secLastLv#) .. T(1);
	* Worst case: skewed tree,  height == n; T(n) + T(n-1) + T(n-2) ... == T(n^2)
     */
    public List<List<Integer>> levelOrderBottom2(TreeNode root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
    	int height = height(root);
    	//go to the last level first, then last-1, last-2 .. first.
    	for(int l=height; l>=1; l--)
    	{
    	    List<Integer> levelList = new ArrayList();
    	    //add this level's nodes to list
    	    addLevelNodes(root, l, levelList);
    	    res.add(levelList);
    	}
    	return res;
    }
    
    void addLevelNodes(TreeNode root, int level, List<Integer> list)
    {
        if(root == null || level <= 0)
            return;
        if(level == 1) // target level
        {
            list.add(root.val);
        }
        else
        {
            addLevelNodes(root.left, level-1, list);
            addLevelNodes(root.right, level-1, list);
        }
    }
    
    int height(TreeNode root)
    {
        if(root == null)
            return 0;
        int lh = height(root.left);
        int rh = height(root.right);
        return (lh > rh) ? lh+1 : rh+1;
    }
}