//package stackpackage;
import java.util.*;
import stackpackage.DoubleDirStack;

// use integer as datatype
public class SetOfStacks{
	private ArrayList<DoubleDirStack> stackList;
	private int capacity;
	public SetOfStacks(int capacity){
		stackList = new ArrayList<DoubleDirStack>();
		this.capacity = capacity;
		stackList.add(new DoubleDirStack(capacity));
	}

	public void push(int item){
		DoubleDirStack topstack = stackList.get(stackList.size() -1 );
		if(topstack.isFull()){
			stackList.add(new DoubleDirStack(capacity));
		}
		stackList.get(stackList.size() -1).push(item);
	}

	public int pop(){
		if(stackList.isEmpty()){
			throw new EmptyStackException();
		}
		DoubleDirStack topstack = stackList.get(stackList.size() -1 );
		if(topstack == null){
			stackList.remove(stackList.size() -1);
		}
		return stackList.get(stackList.size() -1).pop();
	}

	public int popAt(int index){
		if(stackList.get(index) == null){
			throw new EmptyStackException();
		}
		DoubleDirStack topstack = stackList.get(index);
		int thedata = topstack.peek();
		topstack.pop();
		if(topstack.getSize() <= 0)
			stackList.remove(index);
		else if(index != stackList.size() -1){
			shiftLeft(index);
		}
		return thedata;
	}

	public void shiftLeft(int index){
		DoubleDirStack targetstack = stackList.get(index+1);
		if(targetstack == null){
			throw new EmptyStackException();
		}
		int thedata = targetstack.removeBottom();
		if(targetstack.isEmpty())
			stackList.remove(index + 1);
		stackList.get(index).push(thedata);
	}

	public static void main(String [] args){
		SetOfStacks obj = new SetOfStacks(3);
		obj.push(1);
		obj.push(2);
		obj.push(3);
		obj.push(4);
		obj.push(5);
		obj.push(6);
		obj.push(7);
		while(!obj.stackList.isEmpty()){
			System.out.println("popAt: " + obj.popAt(0));
		}
	}
}

