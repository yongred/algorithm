package stackpackage;

import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> { //Dynamic Generic Typing <T>; LIFO
	private Node<T> top; //item to be popped & to be pushed down
	private static class Node<T>{
		private T data;
		private Node<T> next;
		public Node(T data){
			
			this.data = data;
		}
	}
	
	public MyStack(){
		//top = new Node<T> ((T) "bottom");
	}
	
	public void push(T item){
		Node<T> newItem = new Node<T>(item);
		//System.out.println("newItem: " + newItem.data);
		newItem.next = top; //current top goes under one spot
		//System.out.println("top.next: " + top.next.data);
		top = newItem; //new top is the new item
	}
	
	public T pop(){
		if(top == null)
			throw new EmptyStackException();
		T popItem = top.data; 
		//System.out.println("top.next: " + top.next.data);
		top = top.next; //top is now the one under current top
		
		return popItem;
	}
	
	public T peek(){
		if(top == null)
			throw new EmptyStackException();
		return top.data;
	}
	
	public boolean isEmpty(){
		return top == null;
	}
	
	public static void main(String [] args){
		MyStack<Integer> obj = new MyStack<Integer>();
		obj.push(8);
		obj.push(6);
		obj.push(10);
		obj.push(9);
		obj.push(1);
		while(!obj.isEmpty()){
			System.out.println("peek: "+ obj.peek());
			System.out.println("pop: " + obj.pop());
			//System.out.println("peek: "+ obj.peek());
		}
		
	
	}
}



