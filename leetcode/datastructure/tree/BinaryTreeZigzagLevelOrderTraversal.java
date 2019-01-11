/**
103. Binary Tree Zigzag Level Order Traversal
Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]
*/

/**
Solution: Use stack to reverse children for next level.
Ex:
                1
              /   \
            2     3
           /  \    / \
          4  5  6 7
          / \     /  \
         8  9  10 11
res:
[1]
[3,2]
[4,5,6,7]
[11,10,9,8]
Q: 1-, 3-, 2-, 4-, 5-, 6-, 7-, 11, 10, 9, 8;
S: normal, children reverse, L R in LIFO -> R L: 2, 3
reversed, children normal, R L R L in LIFO -> L R L R: 7, 6, 5, 4
normal, children reverse, L R L R in LIFO -> R L R L: 8, 9, 10, 11

How to Arrive:
* Question ask us to reverse every other level. To reverse something, Stack is a good help.
* Problem: adding nodes children in reverse order from parent. 
* Example above, reversed[3, 2] children normal [4,5,6,7] meaning 2's children should be front of 3's, but 2 is poll after 3 node. 
* To solve that, we use Stack to add 3's node first in R L order: 7, 6, then 2's node in R L order: 5, 4; we got 7,6,5,4; now pop them into queue: 4,5,6,7; **Normal order. L R L R.**
* Do the opposite with normal curLevel [4,5,6,7], reversed children [11,10,9,8]; 
* 4 will add L R: [8, 9] to stack, 6 will add L R: [10,11]; then pop them to queue, **Reversed R L R L** 11, 10, 9, 8;
* Time: O(n);
* Space: O(n);

*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class BinaryTreeZigzagLevelOrderTraversal {

  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    // queue and stack.
    Deque<TreeNode> queue = new ArrayDeque<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    // level traverse
    queue.addLast(root);
    boolean reverse = false;
    while (!queue.isEmpty()) {
      int levelsize = queue.size();
      List<Integer> levelList = new ArrayList<>();
      for (int i = 0; i < levelsize; i++) {
        TreeNode curNode = queue.pollFirst();
        levelList.add(curNode.val);
        // check reverse
        if (!reverse) {
          // cur level is normal, add children in reverse order.
          // put in stack LIFO, L R L R to reverse it to (R L R L).
          if (curNode.left != null) {
            stack.addFirst(curNode.left);
          }
          if (curNode.right != null) {
            stack.addFirst(curNode.right);
          } 
        } else {
          // cur lv is reversed, children is normal.
          // put in stack R L R L to become (L R L R).
          if (curNode.right != null) {
            stack.addFirst(curNode.right);
          }
          if (curNode.left != null) {
            stack.addFirst(curNode.left);
          }
        }
      }
      // add curLevelList to resList
      res.add(levelList);
      // next level nodes in stack, pop into queue
      while (!stack.isEmpty()) {
        queue.addLast(stack.removeFirst());
      }
      // flip reverse flag
      reverse = !reverse;
    }
    // reslist level complete
    return res;
  }
  
  
}