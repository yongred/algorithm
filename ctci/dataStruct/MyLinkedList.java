package dataStruct;
/**
 * void add(Object data);
 * void add(Object data, int index);
 * Object get(int index);
 * boolean remove(int index);
 * int size();
 * String toString();
 * private class Node
 * 
 * @author YongLiu
 *
 */
public class MyLinkedList {
	private Node head;
	private int count;
	
	public MyLinkedList(){
		head = new Node(null);
		count = 0;
	}
	
	/*
	 * Appends the specified element to the end of this list.
	 */
	public void add(Object data){
		//temp node store the data, with no next
		Node temp = new Node(data);
		//start counting from head position
		Node current = head;
		//loop till the last node
		while(current.getNext() != null)
		{
			current = current.getNext();
		}
		//link the node with the data to the end
		current.setNext(temp);
		count++;
	}
	
	/*
	 * inserts the specified element at the specified position in this list
	 */
	public void add(Object data, int index){
		Node temp = new Node(data);
		Node current = head;
		for(int i= 0; i<=index && current.getNext() != null; i++){
			//for i <= index, assuming counting from 0 -> (length-1)
			current = current.getNext();
		}
		temp.setNext(current.getNext());
		current.setNext(temp);
		count++;
	}
	
	public Object get(int index){
		Node current = head;
		for(int i =0; i<=index; i++){
			current = current.getNext();
		}
		return current.getData();
	}
	
	public int size(){
		return count;
	}
	
	public boolean remove(Object data){
		
	}
	/*
	 * Node contains a data, and pointer to next data
	 */
	private class Node{
		private Object data;
		private Node next;

		public Node(Object data){
			next = null;
			this.data = data;
		}
		
		public Node(Object data, Node Next){
			this.next = next;
			this.data = data;
		}
		
		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
	}
	
	public static void main(String [] args){
		MyLinkedList ml = new MyLinkedList();
		ml.add(1);
		ml.add(2);
		ml.add(3);
		ml.add(4);
		
		for(int i = 0; i< ml.size(); i++){
			System.out.print(ml.get(i) + ", ");
		}
		System.out.println();
		ml.add(2.5, 6);
		for(int i = 0; i< ml.size(); i++){
			System.out.print(ml.get(i) + ", ");
		}
	}
}
