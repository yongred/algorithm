package cisc3220Alg;

import dataStruct.insertionSort;

public class Hw2p3q4a {
	public void recursiveInsert(int [] A, int keyInd){
		//keyInd start at second index (1)
		if(keyInd > A.length-1)
			return;
		int key = A[keyInd];
		int i= keyInd-1;
		while(i>=0 && A[i]>key){
			A[i+1]= A[i];
			i--;
		}
		A[i+1]= key;
		recursiveInsert(A, keyInd+1);
	}
	
	public static void main(String [] args){
		int [] myInts = {10,30,80,0,40,70,50,60,20};
		Hw2p3q4a obj = new Hw2p3q4a();
		obj.recursiveInsert(myInts, 1);

		for(int i : myInts){
			System.out.print(i + ",");
		}
	}
}
