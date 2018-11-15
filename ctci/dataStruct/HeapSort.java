package dataStruct;

public class HeapSort {
	
	private static int heapsize;
	
	private void swap(int[] A, int x, int y) {
		int temp = A[x];
		A[x] = A[y];
		A[y] = temp;
	}
	
	public void maxHeapify(int [] A, int i){
		//i is the current parent node
		int lft = i *2; //index of left child node is always parent node index * 2
		int rgt = lft +1; //index of right child node = parent index * 2 +1, therefore = lft + 1 
		int largest = i; 
		//compare lft, rgt or parent is the largest
		if(lft <= heapsize && A[lft] > A[largest])
			largest = lft;
		if(rgt <= heapsize && A[rgt] > A[largest])
			largest = rgt;
		if(largest != i){//if lft or rgt is largest
			swap(A, i, largest); //put largest value on top
			//since we change position, we need to compare the value of lft or rgt to their children
			maxHeapify(A, largest);
		}
	}
	
	public void heapsort(int [] A){
		//build a max heap first, by maxHeapify all parents
		//leaves = floor(n/2)+1... n, we only needs to heapify the parents
		heapsize= A.length-1;
		for(int i = heapsize/2; i>=0; i--){
			maxHeapify(A,i);
		}
		//sort
		for(int x= heapsize; x > 0; x--){
			swap(A,0,x); //first element is the largest, switch with last place
			heapsize--; //last place is settle, so heap decreases by 1
			maxHeapify(A, 0); //since switched place, need to maxHeapify first place to make it maxHeap
		}
	}
	
	public static void main(final String[] args)
    {
		HeapSort obj = new HeapSort();
        int[] arr = { 3, 2, 1, 5, 4 };
        
        System.out.println(java.util.Arrays.toString(arr));
        obj.heapsort(arr);
        System.out.println(java.util.Arrays.toString(arr));
    }

}
