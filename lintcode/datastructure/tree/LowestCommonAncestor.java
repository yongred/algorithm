/**
Lowest Common Ancestor:

Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.

The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.

Example
For the following binary tree:

  4
 / \
3   7
   / \
  5   6
LCA(3, 5) = 4

LCA(5, 6) = 7

LCA(6, 7) = 7
*/

public class LowestCommonAncestor {
    public class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }
    /*
       * @param root: The root of the binary search tree.
       * @param A: A TreeNode in a Binary.
       * @param B: A TreeNode in a Binary.
       * @return: Return the least common ancestor(LCA) of the two nodes.
       Solution:
        -if root == A or B, root is the LCA,
        -if left has 1 node, right has 1 node. then root is LCA.
        -if if only one side is found, goes to that side.
        Implements:
        -Find LeftSide first, (eighter Found LCA, 1 of Node, or Null);
        -if(LeftSide == Null) right side will return LCA.
        -if(LeftSide == 1 of Node) (meaning rightSide != NUll), the cur_root is LCA.
        -if(leftSide == LCA) (meaning rightSide == NULL), return leftSideNode
       *Time: O(n); Space: O(lgN);
       */
    public TreeNode LCA(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null)
            return null;
        if (root == A || root == B)
            return root;
        //eighter the LCA or the 1 of A,B is returned.
        TreeNode left = LCA(root.left, A, B);
        //left not found either A and B, then it must be on the right side;
        if (left == null) {
            return LCA(root.right, A, B);
        }
        //left found A or B, check right branch.
        TreeNode right = LCA(root.right, A, B);
        //if right branch found A or B, means 1 on left, 1 on right of root, so root is the LCA.
        if (right != null)
            return root;
        //if right branch not found any. meaning both are on left branch. Which Means the return Node left is LCA.
        return left;

    }

}