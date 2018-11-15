
package stackpackage;
/**
use int as datatype
*/
import java.util.*;

public class DoubleDirStack{
	public class Node{
		private Node above, below;
		private int data;
		public Node(int data){
			this.data = data;
		}
	}
	private int capacity;
	private int size;
	private Node bottom, top;

	public DoubleDirStack(int capacity) {
		this.capacity = capacity;
		size = 0;
	}

	public boolean isFull(){
		return size >= capacity;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public void join(Node below, Node above){
		if(below != null) below.above = above;
		if(above != null) above.below = below;
	}

	public void push(int item){
		if(isFull()){
			try{
				throw new Exception("full stack");
			}catch(Exception e){

			}
		}
		Node newTop = new Node(item);
		if(isEmpty()){
			bottom = newTop;
		}
		join(top, newTop);
		top = newTop;
		size++;

	}

	public int pop(){
		if(isEmpty()){
			throw new EmptyStackException();
		}
		int value = top.data;
		top = top.below;
		size--;
		return value;
	}

	public int removeBottom(){
		Node btm = bottom;
		bottom = bottom.above;
		if(bottom != null)
			bottom.below = null;
		return btm.data;
	}

	public int peek(){
		if(isEmpty()){
			throw new EmptyStackException();
		}
		return top.data;
	}

	public int getSize(){
		return size;
	}

	public Node getTop(){
		return top;
	}

	public Node getBottom(){
		return bottom;
	}
/*
	public static void main(String [] args){
		DoubleDirStack obj = new DoubleDirStack(10);
		obj.push(8);
		obj.push(6);
		obj.push(10);
		obj.push(9);
		obj.push(1);
		while(!obj.isEmpty()){
			System.out.println("peek: "+ obj.peek());
			System.out.println("pop: " + obj.pop());
			System.out.println("remove bottom: " + obj.removeBottom());
		}
		
	}*/

}//end DoubleDirStack






