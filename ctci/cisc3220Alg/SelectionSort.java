
/**
 Consider sorting n numbers stored in array A by first finding the smallest element
of A and exchanging it with the element in A[1]. Then find the second smallest
element of A, and exchange it with A[2]. Continue in this manner for the first n-1
elements of A. Write pseudocode for this algorithm, which is known as selection
sort.
 * @author YongLiu
 *
 */
public class SelectionSort {
	public void selectionSort(int [] A){
		for(int i = 0; i < A.length-1; i++){
			int minIndex = i;
			for(int j= i+1; j< A.length; j++){
				if(A[j] < A[minIndex]){
					minIndex = j;
				}	
			}
			if(minIndex != i){
				int temp = A[i];
				A[i] = A[minIndex];
				A[minIndex] = temp;
			}
		}
	}
	public static void main(String [] args){
		int [] myInts = {10,30,80,0,40,70,50,60,20};
		Hw2p2q2 obj = new Hw2p2q2();
		obj.selectionSort(myInts);

		for(int i : myInts){
			System.out.print(i + ",");
		}
	}
}
