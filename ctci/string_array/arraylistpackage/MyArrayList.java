/**
array list implement
*/
/*
 Amortized Time
 As we insert elements, we double the capacity when the size of the array is a power of 2.50 after X elements,
we double the capacity at array sizes 1, 2, 4, 8, 16, ... , X. That doubling takes, respectively, 1, 2, 4, 8, 16, 32, 64, ... , X copies.
What is the sum of 1 + 2 + 4 + 8 + 16 + ... + X? 
If you read this sum left to right, it starts with 1 and doubles
until it gets to X. If you read right to left, it starts with X and halves until it gets to 1.
What then is the sum of X + x/2 + x/4 + x/8 + ... + 1? This is roughly 2X.
Therefore, X insertions take O(2X) time. The amortized time for each insertion is O(1).
*/
package arraylistpackage;
import java.util.*;

public class MyArrayList{
	Object [] theArr;
	int sizeNow = 0;
	public MyArrayList(){
		//default size 10
		theArr = new Object[10];
	}

	public void add(Object item){
		if(theArr.length - sizeNow <= 1){
			increaseCapacity();
		}
		//arr[i++] = val; meaning arr[i]= val, i=i+1
		//arr[++i] = val; meaning i=i+1, arr[i]= val
		theArr[sizeNow++] = item;
	}

	public Object get(int index){
		if(index < sizeNow)
			return theArr[index];
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	public Object remove(int index){
		//after delete element shift all after elements to left one place
		if(index >= sizeNow)
			throw new ArrayIndexOutOfBoundsException();
		Object value = theArr[index];
		for(int i=index; i< sizeNow - 1; i++){
			//shift left to fill index place
			theArr[i] = theArr[i+1];
		}
		//last element to null
		theArr[sizeNow-1] = null;
		sizeNow--;
		return value;
	}

	public int size(){
		return sizeNow;
	}

	public void increaseCapacity(){
		//double capacity when max reached
		Object [] newArr = new Object[theArr.length * 2];
		for(int i = 0; i< theArr.length; i++){
			newArr[i] = theArr[i];
		}
		System.out.println("new length "+ newArr.length);
		theArr = newArr;
	}

	public static void main(String [] args){
		
		MyArrayList list = new MyArrayList();
		for(int x=0; x< 30; x++)
			list.add(x);
		list.remove(29);
		list.remove(0);
		list.remove(7);
		for(int y=0; y< list.size(); y++){
			System.out.println("index " + y + " value " + list.get(y));
		}

	}
}