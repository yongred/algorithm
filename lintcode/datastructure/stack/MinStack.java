/**
Min Stack:
Implement a stack with min() function, which will return the smallest number in the stack.
LIFO
It should support push, pop and min operation all in O(1) cost.
*/

import java.util.*;
import java.io.*;


public class MinStack {
    /*
    * @param a: An integer
    */
    public MinStack(int a) {
        // do intialization if necessary
        this.top = new Node<Integer>(a);
    }

    static class Node<Integer>{
		private Node<Integer> next;  //node under this node
		private int data;
		private int minSubStack; //smallest value in the subStack
		public Node(int data){
			this.data = data;
			this.minSubStack = data;
		}
	}
	protected Node<Integer> top;  //top node

    /*
     * @param number: An integer
     * @return: nothing
     */
    public void push(int number) {
        Node<Integer> new_top = new Node<Integer>(number);
        new_top.next = top;
        top = new_top;
        setMin();
    }

    /*
     * @param a: An integer
     * @return: An integer
     */
    public int pop(int a) {
    	if(top == null){
			throw new EmptyStackException();
		}
        int pop_val = top.data;
        top = top.next;
        return pop_val;
    }


    public void setMin(){
		if(top.next == null)
			return;
		// '<' only compare primitives
		if(top.next.minSubStack < top.minSubStack){
			//set minSubStack by compare to the node under it, b/c the last top already compared with all other elements
			top.minSubStack = top.next.minSubStack;
		}
	}



    /*
     * @param a: An integer
     * @return: An integer
     */
    public int min(int a) {
    	//return minimum value in the Stack
		if(top == null){
			throw new EmptyStackException();
		}
        return top.minSubStack;
    }

    public int peek(){
		if(top == null){
			throw new EmptyStackException();
		}

		return top.data;
	}

    public boolean isEmpty(){
		return top == null;
	}

    public static void main(String [] args){
		MinStack obj = new MinStack(3);
		obj.push(8);
		obj.push(6);
		obj.push(10);
		obj.push(9);
		obj.push(1);
		while(!obj.isEmpty()){
			System.out.println("minimum: " + obj.min(-1));
			System.out.println("peek: "+ obj.peek());
			System.out.println("pop: " + obj.pop(-1));
		}
		
	}
}