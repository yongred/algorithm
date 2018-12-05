
import java.util.Arrays;

public class MergeSort {
	public void mergeSort(int[] A, int start, int end) {
    if (start >= end) {
      return;
    }
    int mid = start + (end - start) / 2;
    mergeSort(A, start, mid);
    mergeSort(A, mid + 1, end);
    merge(A, start, mid, end);
  }
  
  public void merge(int[] A, int start, int mid, int end) {
    // left size. include mid
    int n1 = mid - start + 1;
    // right size. Exclude mid
    int n2 = end - mid;
    // create temp arrs to merge vals, so A[n] won't be overrided.
    // ex: [2,3] [1,4] A[2,3,1,4]; after 1st iter, A[1,3,1,4] [1,3] [1,4] 2 missed.
    int[] left = new int[n1];
    for (int i = start; i <= mid; i++) {
      left[i - start] = A[i];
    }
    int[] right = new int[n2];
    for (int i = mid + 1; i <= end; i++) {
      right[i - (mid + 1)] = A[i];
    }
    
    int i1 = 0;
    int i2 = 0;
    int arrIndex = start;
    while (i1 < n1 && i2 < n2) {
      if (left[i1] <= right[i2]) {
        A[arrIndex] = left[i1];
        i1++;
      } else {
        // right < left
        A[arrIndex] = right[i2];
        i2++;
      }
      arrIndex++;
    }
    // if left is not finished
    while (i1 < n1) {
      A[arrIndex] = left[i1];
      i1++;
      arrIndex++;
    }
    // if right is not finished.
    while (i2 < n2) {
      A[arrIndex] = right[i2];
      i2++;
      arrIndex++;
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
