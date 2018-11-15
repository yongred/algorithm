
import java.util.Arrays;

public class MergeSort {
	public void mergeSort(int A[], int str,int end){
		//when length <= 1
		if(str >= end)
			return;
		int mid = (str + end)/2;
		//slide them
		mergeSort(A, str, mid); //left side of array
		mergeSort(A, mid+1, end); //right side of array
		//merge together
		merge(A, str, mid, end);
	}
	public void merge(int [] A, int str, int mid, int end){
		int leftInd = str;
		int rightInd = mid+1;
		int temp[] = new int[A.length];
		for(int x = str; x<= end; x++){
			temp[x] = A[x];
		}
		int tmpInd = str;
		//compare A[str-mid] to A[mid-end], put into temp[str-end]
		while(leftInd <= mid && rightInd <= end){
			if(A[leftInd] < A[rightInd]){
				temp[tmpInd] = A[leftInd];
				leftInd++;
			}else{
				temp[tmpInd] = A[rightInd];
				rightInd++;
			}
			tmpInd++;
		}
		//either left array or right array is not completely put into temp
		if(leftInd > mid){
			for(int r = rightInd; r<= end; r++){
				temp[tmpInd] = A[r];
				tmpInd++;
			}
		}else{
			for(int l = leftInd; l<= mid; l++){
				temp[tmpInd] = A[l];
				tmpInd++;
			}
		}
		for(int i = str; i<= end; i++){
			A[i] = temp[i];
		}
	}
	public static void main(String [] args){
		int [] myInts = {10,30,80,0,40,70,50,60,20};
		Hw2p3q2 obj = new Hw2p3q2();
		obj.mergeSort(myInts, 0, myInts.length -1);

		for(int i : myInts){
			System.out.print(i + ",");
		}
	}
}
