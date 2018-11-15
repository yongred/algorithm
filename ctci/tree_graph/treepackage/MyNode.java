/**
My tree Node implementation
*/

package treepackage;
import java.util.*;

public class MyNode<T>{
	private List<MyNode<T>> children = new ArrayList<MyNode<T>>();
	private MyNode<T> parent = null;
	private T data = null;

	public MyNode(){
		
	}
	public MyNode(T data){
		this.data = data;
	}
	public MyNode(T data, MyNode<T> parent){
		this.data = data;
		setParent(parent);
	}

	public List<MyNode<T>> getChildren(){
		return children;
	}

	public void setParent(MyNode<T> parent){
		parent.getChildren().add(this);
		this.parent = parent;
	}

	public MyNode<T> getParent(){
		return parent;
	}

	public void addChild(T data){
		MyNode<T> child = new MyNode<T>(data);
		child.setParent(this);
		//this.children.add(child);  //added to children list
	}

	public void addChild(MyNode<T> child){
		child.setParent(this);
		//this.children.add(child);
	}

	public T getData(){
		return this.data;
	}

	public void setData(T data){
		this.data = data;
	}

	public boolean isRoot(){
		return this.parent == null;
	}

	public boolean isLeaf(){  //no child
		return (this.children.size() == 0);
	}

	public void removeParent(){
		this.parent = null;
	}

	public void printTree(MyNode<T> root){   
		System.out.print(root.getData() + " ");
		if(!root.isLeaf()){
			System.out.println();
			for(MyNode<T> node : root.getChildren()){
				printTree(node);
			}
			System.out.println();			
		}

	}

	public static void main(String [] args){
		MyNode<String> parentNode = new MyNode<String>("Parent"); 
		MyNode<String> childNode1 = new MyNode<String>("Child 1", parentNode);
		MyNode<String> childNode2 = new MyNode<String>("Child 2");     

		childNode2.setParent(parentNode); 

		MyNode<String> grandchildNode = new MyNode<String>("Child of childNode1", childNode1); 
		List<MyNode<String>> childrenNodes = parentNode.getChildren();
		parentNode.printTree(parentNode);
	}
}