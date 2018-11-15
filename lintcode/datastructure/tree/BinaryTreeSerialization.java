/**
Binary Tree Serialization:
Design an algorithm and write code to serialize and deserialize a binary tree. Writing the tree to a file is called 'serialization' and reading back from the file to reconstruct the exact same binary tree is 'deserialization'.
*/
import java.util.*;
import java.io.*;

public class BinaryTreeSerialization{
	public class TreeNode {
      public int val;
      public TreeNode left, right;
      public TreeNode(int val) {
          this.val = val;
          this.left = this.right = null;
      }
  }

  public void connect(TreeNode parent, TreeNode child, boolean isLeft){
		//child.parent = parent;
		if(isLeft)
			parent.left = child;
		else
			parent.right = child;
	}

  /**
   * This method will be invoked first, you should design your own algorithm 
   * to serialize a binary tree which denote by a root node to a string which
   * can be easily deserialized by your own "deserialize" method later.
   */
  public String serialize(TreeNode root) {
    if(root == null)
    	return "";

    StringBuilder sb = new StringBuilder();
    LinkedList<TreeNode> list = new LinkedList<TreeNode>();
    list.add(root);
    while(!list.isEmpty()){
    	TreeNode cur = list.poll();
    	if(cur != null){
    		sb.append(String.valueOf(cur.val) + ",");
    		list.add(cur.left);
    		list.add(cur.right);
    	}else{
    		sb.append("#,");
    	}
    }
    //last char ','
    sb.deleteCharAt(sb.length()-1);
    return sb.toString();
  }
    
  /**
   * This method will be invoked second, the argument data is what exactly
   * you serialized at method "serialize", that means the data is not given by
   * system, it's given by your own serialize method. So the format of data is
   * designed by yourself, and deserialize it here as you serialize it in 
   * "serialize" method.
   */
  public TreeNode deserialize(String data) {
  	if(data==null || data.length()==0)
        return null;
  	String [] nodes = data.split(",");
  	TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
  	
  	LinkedList<TreeNode> list = new LinkedList<TreeNode>();
  	list.add(root);

  	int i=1;
  	while(!list.isEmpty()){
  		TreeNode cur = list.poll();
  		if(cur == null)
  			continue;
  		if(!nodes[i].equals("#")){
  			cur.left = new TreeNode(Integer.parseInt(nodes[i]));
  			list.offer(cur.left);
  		}else{
  			cur.left = null;
  			list.offer(null);
  		}
  		i++;

  		if(!nodes[i].equals("#")){
  			cur.right = new TreeNode(Integer.parseInt(nodes[i]));
  			list.offer(cur.right);
  		}else{
  			cur.right = null;
  			list.offer(null);
  		}
  		i++;

  	}
  	return root;
  }

  public static void inorder(TreeNode root){
  	if(root == null)
  		return;
  	System.out.print(root.val + ",");
  	inorder(root.left);
  	inorder(root.right);
  }

  public static void main(String [] args){
		BinaryTreeSerialization obj = new BinaryTreeSerialization();
		BinaryTreeSerialization.TreeNode node1 = obj.new TreeNode(1);
		BinaryTreeSerialization.TreeNode node2 = obj.new TreeNode(2);
		BinaryTreeSerialization.TreeNode node3 = obj.new TreeNode(3);
		BinaryTreeSerialization.TreeNode node4 = obj.new TreeNode(4);
		BinaryTreeSerialization.TreeNode node5 = obj.new TreeNode(5);
		BinaryTreeSerialization.TreeNode node6 = obj.new TreeNode(6);
		BinaryTreeSerialization.TreeNode node7 = obj.new TreeNode(7);
		BinaryTreeSerialization.TreeNode node8 = obj.new TreeNode(8);
		BinaryTreeSerialization.TreeNode node9 = obj.new TreeNode(9);
		BinaryTreeSerialization.TreeNode node10 = obj.new TreeNode(10);
		BinaryTreeSerialization.TreeNode node11 = obj.new TreeNode(11);
		BinaryTreeSerialization.TreeNode node12 = obj.new TreeNode(12);

		obj.connect(node1, node2, true);//node1.left = node2;
		obj.connect(node1, node3, false);//node1.right = node3;
		obj.connect(node2, node4, true);//node2.left = node4;
		obj.connect(node2, node5, false);//node2.right = node5;
		obj.connect(node3, node6, true);//node3.left = node6;
		obj.connect(node3, node7, false);//node3.right = node7;
		obj.connect(node7, node9, false);//node7.right = node9;
		//node7.left = node8;
		obj.connect(node9, node10, false);//node9.right = node10;
		obj.connect(node10, node11, false);//node10.right = node11;
		obj.connect(node11, node12, false);//node11.right = node12;

		String serialized = obj.serialize(node1);
		System.out.println(serialized);
		BinaryTreeSerialization.TreeNode ans = obj.deserialize(serialized);
		obj.inorder(ans);

	}
}

