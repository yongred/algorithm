/**
3.2 Stack Min: How would you design a stack which, in addition to push and pop, has a function min
which returns the minimum element? Push, pop and min should all operate in 0(1) time.
Hints: #27, #59, #78
*/
import java.util.*;

public class MinStack<T extends Comparable<T>>{
	public MinStack(){

	}

	static class Node<T>{
		private Node<T> next;
		private T data;
		private T minSubStack; //smallest value in the subStack
		public Node(T data){
			this.data = data;
			this.minSubStack = data;
		}
	}
	protected Node<T> top;

	public T getMin(){
		//return minimum value in the Stack
		if(top == null){
			throw new EmptyStackException();
		}
		return top.minSubStack;
	}

	public void setMin(){
		if(top.next == null)
			return;
		// '<' only compare primitives
		if(top.next.minSubStack.compareTo(top.minSubStack) < 0){
			//set minSubStack by compare to the node under it, b/c the last top already compared with all other elements
			top.minSubStack = top.next.minSubStack;
		}
	}

	public void push(T item){
		Node<T> newTop = new Node<T>(item);
		newTop.next = top; //current top goes under
		top = newTop; //becomes top
		setMin();
	}

	public T pop(){
		if(top == null){
			throw new EmptyStackException();
		}
		T value = top.data;
		top = top.next;

		return value;
	}

	public T peek(){
		if(top == null){
			throw new EmptyStackException();
		}

		return top.data;
	}

	public boolean isEmpty(){
		return top == null;
	}
	public static void main(String [] args){
		MinStack<Integer> obj = new MinStack<Integer>();
		obj.push(8);
		obj.push(6);
		obj.push(10);
		obj.push(9);
		obj.push(1);
		while(!obj.isEmpty()){
			System.out.println("minimum: " + obj.getMin());
			System.out.println("peek: "+ obj.peek());
			System.out.println("pop: " + obj.pop());
		}
		
	}

}//end MinStack




