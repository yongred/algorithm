/**
3.1 Three in One: Describe how you could use a single array to implement three stacks.
Hints: #2, #72, #38, #58
*/

import stackpackage.*;
import java.util.*;

//circular shifting stacks
public class ThreeInOne{
	int [] dataArr;	//datas
	int arrCapacity;
	StackInfo [] stackInfos;
	int stackSize; //how many stacks

	public class StackInfo{
		public int strInd, sizeNow, capacity;
		public StackInfo(int strInd, int capacity){
			this.strInd = strInd;
			this.capacity = capacity;
			sizeNow = 0;
		}

		/* Check if an index on the full array is within the stack boundaries. The stack can wrap around to the start of the array. */
		public boolean isWithinCapacity(int ind){
			if(ind < 0 || ind > arrCapacity -1)
				return false;
			//if wrap around adjust, check when index < strInd
			//wrap around ind becomes bigger num, ex: 2 + 10 = 12, ind 0-9
			int adjInd = (ind < strInd) ? ind + arrCapacity : ind;
			int lastCapInd = strInd + capacity - 1; //last ind of stack capacity
			return strInd <= adjInd && adjInd <= lastCapInd;
		}
		//ind of last stack capacity
		public int getLastCapInd(){
			return adjustedInd(strInd + capacity -1);
		}
		//ind of last element
		public int getLastElementInd(){
			//System.out.println("str: " + strInd + " lastInd " + adjustedInd(strInd + sizeNow -1));
			return adjustedInd(strInd + sizeNow -1);
		}

		public boolean isFull(){
			return sizeNow >= capacity;
		}
		public boolean isEmpty(){
			return sizeNow <= 0;
		}

	}

	public ThreeInOne(int stackSize, int stackCapacity){
		this.stackSize = stackSize;
		arrCapacity = stackSize * stackCapacity;
		dataArr = new int[arrCapacity];
		stackInfos = new StackInfo[stackSize];
		for(int i=0; i< stackSize; i++){
			//divide equally when initialize
			stackInfos[i] = new StackInfo(stackCapacity * i, stackCapacity);
		}
	} 

	//chose a stack to push element
	public void push(int stackInd, int data){
		//no more space
		if(getTotalSize() >= arrCapacity){
			System.out.println("totalsize: " + getTotalSize() + " arrCapacity " + arrCapacity);
			try{
				throw new Exception();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//System.out.println(" StackInd " + stackInd + " data " + data);
		StackInfo stack = stackInfos[stackInd];
		if(stack.isFull()){
			//System.out.println("isFull");
			//stack full get space from other stack
			expand(stackInd);
		} 
		stack.sizeNow++;
		int lastInd = stack.getLastElementInd();
		//System.out.println("lastInd: " + lastInd);
		dataArr[lastInd] = data;
		
	}

	//choose a stack to pop, LIFO
	public int pop(int stackInd){
		StackInfo stack = stackInfos[stackInd];
		if(stack == null || stack.isEmpty())
			throw new EmptyStackException();
		int lastInd = stack.getLastElementInd();
		int data = dataArr[lastInd];
		dataArr[lastInd] = Integer.MIN_VALUE; //delete it
		stack.sizeNow--;
		return data;
	}

	//peek at specific stack, LIFO, last element
	public int peek(int stackInd){
		StackInfo stack = stackInfos[stackInd];
		if(stack == null || stack.isEmpty())
			throw new EmptyStackException();
		int lastInd = stack.getLastElementInd();
		return dataArr[lastInd];
	}

	//expand target stack
	public void expand(int stackInd){
		shiftStack((stackInd + 1) % stackSize); //shift next stack, to expand current stack
		stackInfos[stackInd].capacity++; //gain 1 space from next stack
	}
	//shift & shrink this stack capacity for prev stack to expand
	public void shiftStack(int stackInd){
		StackInfo stack = stackInfos[stackInd];

		if(stack.isFull()){ //if this stack is full, shift next stack
			shiftStack((stackInd + 1) % stackSize);
			stack.capacity++; //gain 1 capacity from shifting & shrink next stack
		}
		//ind for looping
		int curInd = nextInd(stack.getLastElementInd());
		System.out.println("stackInd " + stackInd);
		while(stack.isWithinCapacity(curInd)){
			
			//start from last ind move to strInd, copy val to next space
			System.out.println("curInd " + curInd + " value " + dataArr[curInd]);
			//System.out.println("prevInd " + prevInd(curInd));
			dataArr[curInd] = dataArr[prevInd(curInd)];
			System.out.println("assigning " + curInd + " to " + dataArr[prevInd(curInd)]);
			curInd = prevInd(curInd);
			if(curInd == stack.strInd)
				break;
		}
		//System.out.println("strval: " + dataArr[stack.strInd]);
		//start data remove
		dataArr[stack.strInd] = Integer.MIN_VALUE;
		stack.strInd = nextInd(stack.strInd);
		stack.capacity--;
		System.out.println("stackInd "+ stackInd +  " capacity: " + stack.capacity);
		//printStack(stackInd);
	}
	//total elements in arr, all stacks
	public int getTotalSize(){
		int size = 0;
		for(StackInfo st : stackInfos){
			size += st.sizeNow;
		}
		return size;
	}
	//if index larger than arr size, adjust it as circular ind
	public int adjustedInd(int ind){
		//prevent - ind, in java ex: -1 % 10 = -1, -5 % 5 = 0; -5/5 = -1 R=0; so, -1 + 10 = 9, 9 % 10 = 9; so after 0 ind goes to 9
		//other programming language may uses a % b = a - floor(a/b) * b;
		//ex: -1 - floor(-1/10) * 10; -1 - floor(-0.1) * 10; -1 - -1 * 10; -1 - -10 = 9; this is what we want in this case
		int max = arrCapacity;
		int adjInd = ((ind % max) + max) % max; //ind is 0 - (max-1);
		return adjInd; 
	}
	//get next index, adjust for wrap around
	public int nextInd(int ind){
		return adjustedInd(ind + 1);
	}
	//get prev index, adjust for wrap around
	public int prevInd(int ind){
		return adjustedInd(ind - 1);
	}

	public void printStack(int stackInd){
		StackInfo stack = stackInfos[stackInd];
		int ind = stack.strInd;
		while(ind-1 != stack.getLastElementInd()){
			System.out.print(" " + dataArr[ind]);
			ind = nextInd(ind);
		}
		System.out.println();
	}

	public static void main(String [] args){
		ThreeInOne obj = new ThreeInOne(3, 10);
		for(int i=0; i< 30; i++){
			if(i< 8)
				obj.push(0, i);
			else if(i >= 8 && i < 18)
				obj.push(2, i);
			else if(i >= 18)
				obj.push(1, i);
		}
		obj.printStack(0);
		obj.printStack(1);
		obj.printStack(2);
		System.out.println("peek " + obj.peek(1));
		System.out.println("pop " + obj.pop(1));
		obj.printStack(0);
		obj.printStack(1);
		obj.printStack(2);
	}
}