package stackpackage;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;


public class MyQueue<T> { //FIFO
	private Node<T> first; //where to dequeue
	private Node<T> last; // enqueue on top of it
	private static class Node<T>{
		private T data;
		private Node<T> next;
		public Node(T data){
			
			this.data = data;
		}
	}
	
	public MyQueue(){
		
	}
	
	public void enqueue(T item){
		Node<T> newItem = new Node<T>(item);
		if(last != null)
			last.next = newItem; //next space after current
		last = newItem; //newItem is last
		//System.out.println("last: " + last.data);
		if(first == null)
			first = last; //first.data = 1st item, first.next = newItem. 
	
	}
	
	public T dequeue(){
		if(first == null)
			throw new NoSuchElementException();
		T dequeueItem = first.data;
		//System.out.println("firstNext: " + first.next.data);
		first = first.next; //first is now the one above current
		
		if(first == null)
			last = null;
		return dequeueItem;
	}
	
	public T peek(){
		if(first == null)
			throw new EmptyStackException();
		return first.data;
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public static void main(String [] args){
		MyQueue<Integer> obj = new MyQueue<Integer>();
		obj.enqueue(1);
		obj.enqueue(2);
		obj.enqueue(3);
		obj.enqueue(4);
		obj.enqueue(5);
		while(!obj.isEmpty()){
			System.out.println("peek: "+ obj.peek());
			System.out.println("pop: " + obj.dequeue());
			//System.out.println("peek: "+ obj.peek());
		}
	}
}
