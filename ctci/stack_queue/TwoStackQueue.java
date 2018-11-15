
import java.util.*;

public class TwoStackQueue<T>{
	private Stack<T> newestStack;
	private Stack<T> oldestStack;

	public TwoStackQueue(){
		newestStack = new Stack();
		oldestStack = new Stack();
	}

	public void enqueue(T item){
		newestStack.push(item);
	}

	public void intoOldest(){
		if(oldestStack.isEmpty()){
			while(!newestStack.isEmpty()){
				oldestStack.push(newestStack.pop());
			}
		}
	}

	public T dequeue(){
		intoOldest();
		return oldestStack.pop();
	}

	public T peek(){
		intoOldest();
		return oldestStack.peek();
	}

	public int size(){
		return oldestStack.size() + newestStack.size();
	}

	public static void main(String [] args){
		TwoStackQueue obj = new TwoStackQueue();
		obj.enqueue(1);
		obj.enqueue(2);
		obj.enqueue(3);
		obj.enqueue(4);
		obj.enqueue(5);
		obj.enqueue(6);
		obj.enqueue(7);
		while(obj.size() > 0){
			System.out.println("peek: " + obj.peek());
			System.out.println("dequeue: " + obj.dequeue());
		}
	}
}