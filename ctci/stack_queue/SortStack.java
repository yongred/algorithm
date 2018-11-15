/**
3.S Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use
an additional temporary stack, but you may not copy the elements into any other data structure
(such as an array). The stack supports the following operations: push, pop, peek, and isEmpty.
Hints: # 15, #32, #43
*/
import java.util.*;

public class SortStack{
/*pop elements into tmpStack largest on top sorted, then put rest 
of the primary stack elements into tmpStack in right sorted place.
use tmp variable to hold element that need to insert into tmpStack, if top element of tmp stack is bigger than holding element, then put it in primary stack first for next pop in after cur element.
*/
	public void stackSort(Stack<Integer> theStack){
		Stack<Integer> tmpStack = new Stack<Integer>();
		while(!theStack.isEmpty()){
			int element = theStack.pop(); //current element to insert into tmp stack
			while(!tmpStack.isEmpty() && tmpStack.peek() > element){
			//if top element is bigger than cur holding element, lift it out first, then put it back later after current element 
				theStack.push(tmpStack.pop());
			}
			tmpStack.push(element);
		}
		while(!tmpStack.isEmpty()){
			theStack.push(tmpStack.pop());
		}
	}

	public static void main(String [] args){
		SortStack obj = new SortStack();
		Stack theStack = new Stack();
		theStack.push(1);
		theStack.push(6);
		theStack.push(5);
		theStack.push(3);
		theStack.push(4);
		theStack.push(2);
		obj.stackSort(theStack);
		
		while(!theStack.isEmpty()){
			System.out.print(" "+ theStack.pop() );
		}
	}
}