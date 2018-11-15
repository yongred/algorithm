/**
singly linked list
*/

package linkedlistpackage;
import java.util.*;

public class SinglyLinkedList<T>{
	
	public class Node{
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
		
	}

	private Node head; 
	private int size = 0;

	public SinglyLinkedList(){
		

	}
	//add element to end of the list (tail)
	public void add(T data){
		Node newNode = new Node(data); 
		if(head == null){
			//System.out.println(sentinal);
			head = newNode;
			size++;
			return;
		}
		Node cur = head;
		while(cur.getNext() != null){
			cur = cur.getNext();
		}
		cur.setNext(newNode);
		size++;
	}

	public Node search(T item){
		if(head.getData() == item)
			return head;
		Node curNode = head;
		while(curNode.getNext() != null){
			if(curNode.getNext().getData() == item)
				return curNode.getNext();
			curNode = curNode.getNext();
		}
		return null;
	}

	public T delete(T item){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		if(head.getData() == item){
			T value = (T)head.getData();
			head = head.getNext();
			size--;
			return value;
		}

		Node cur = head;
		while(cur.getNext() != null){
			if(cur.getNext().getData() == item){
				cur.setNext(cur.getNext().getNext());
				size--;
				return (T)cur.getNext().getData();
			}
			cur = cur.getNext();
		}
		
		size--;
		return null;
	}

	public boolean isEmpty(){
		return size <= 0;
	}
	public int getSize(){
		return size;
	}
	public Node getHead(){
		return head;
	}
	public void setHead(Node head){
		this.head = head;
	}

	public static void main(String [] args){
		SinglyLinkedList obj = new SinglyLinkedList();
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
	}


}