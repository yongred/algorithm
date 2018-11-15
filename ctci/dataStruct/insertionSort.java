package dataStruct;

/**
 * like selecting blinded poker cards, and placing it to your hand.
 * the new card (key) compare to old cards (which already sorted) and place to the 
 * right position. ex: if 3, 9, 10 is on hand, new card 5 will compare to 10, and 9
 * and then place it in 2nd position 3,5,9,10. 
 */
import cisc3220Alg.Hw2p3q4;

public class insertionSort {
	public void insertSort(int [] arr){
		if(arr.length < 2)
			return;
		for(int rt= 1; rt< arr.length; rt++){
			int key = arr[rt]; //key start with sec element
			//insert arr[rt] into sorted array arr[0...rt-1]
			int lf= rt-1; //left start 1 index less than rt
			while(lf >= 0 && arr[lf] > key){ //if left values has bigger
				//move lf value (which is bigger than key) to 1 pos right							
				arr[lf+1]= arr[lf];
				lf--;
			}
			//lf+1 because lf is decremented to next (like -1) 
			arr[lf+1]= key;
		}
	}
	
	public static void main(String [] args){
		int [] myInts = {10,30,80,0,40,70,50,60,20};
		insertionSort obj = new insertionSort();
		obj.insertSort(myInts);

		for(int i : myInts){
			System.out.print(i + ",");
		}
	}
}
