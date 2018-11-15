/**
head has no Prev, leftmost element.
tail has no next, rightmost element.
sentinal connect circularly next to head, prev to tail
*/
package linkedlistpackage;
import java.util.*;

public class MyLinkedList<T>{
	
	public class Node{
		private Node prev;
		private Node next;
		private Object data;
		public Node(Object data){
			this.data = data;
		}
		public Object getData(){
			return data;
		}
		public void setData(Object data){
			this.data = data;
		}
		public Node getNext(){
			return next;
		}
		public void setNext(Node next){
			this.next = next;
		}
		public Node getPrev(){
			return prev;
		}
		public void setPrev(Node prev){
			this.prev = prev;
		}
	}

	private Node sentinal; //next = head, prev = tail
	private int size = 0;

	public MyLinkedList(){
		sentinal = new Node("sentinal");
		sentinal.setNext(sentinal);
		sentinal.setPrev(sentinal);

	}
	//add element to end of the list (tail)
	public void add(T data){
		Node newNode = new Node(data); 
		if(sentinal.getNext() == sentinal){
			//System.out.println(sentinal);
			sentinal.setNext(newNode);
			newNode.setNext(sentinal);

			sentinal.setPrev(newNode);
			newNode.setPrev(sentinal);
			size++;
			return;
		}

		Node curTail = sentinal.getPrev();
		curTail.setNext(newNode);
		newNode.setPrev(curTail);
		newNode.setNext(sentinal);
		sentinal.setPrev(newNode);
		size++;
	}

	public Node search(T item){
		Node curNode = sentinal.getNext();
		while(curNode != sentinal && curNode.getData() != item){
			curNode = curNode.getNext();
		}
		return curNode;
	}

	public T delete(T item){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		Node targetNode = search(item);
		System.out.println("prev " + targetNode.getPrev().getData());
		System.out.println("next " + targetNode.getNext().getData());

		targetNode.getPrev().setNext(targetNode.getNext()); //connect prev - next
		targetNode.getNext().setPrev(targetNode.getPrev()); //connect next - prev
		
		size--;
		return (T)targetNode.getData();
	}

	public boolean isEmpty(){
		return size <= 0;
	}
	public int getSize(){
		return size;
	}

	public Node getSentinal(){
		return sentinal;
	}

	/*public static void main(String [] args){
		MyLinkedList obj = new MyLinkedList();
		obj.add(1);
		obj.add(2);
		obj.add(3);
		obj.add(4);
		obj.add(5);
		int size =  obj.getSize();
		for(int i=1; i<= size; i++){
			System.out.println(obj.delete(i));
			for(int j=i+1; j<= size; j++){
				System.out.print(" " + obj.search(j).getData());
			}
			System.out.println();
		}
	}*/
} 