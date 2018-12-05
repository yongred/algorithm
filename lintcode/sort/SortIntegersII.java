/**
464. Sort Integers II
Given an integer array, sort it in ascending order. Use quick sort, merge sort, heap sort or any O(nlogn) algorithm.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
*/

/**
Solution: Quick Sort
* partition and return the pivot index, where all elms left <= pivot, right > pivot.
* Ex: [4,5,2,1,3] pv=3; becomes [2,1,3,4,5];
* Then just sort [start->pivot-1] and sort [pivot+1 -> end];
* Partition we need to make sure we **keep track of the pivotIndex so we can return at the end**.
* To do that we can swap pivot into the correct position last, and return that pos.
*  **Key**: We can have a *pivotIndex variable*, then we **put all elms <= pivot before pivotIndex variable**.
* So we know the pivotIndex is at the correct pos after all elms <= pivot moved to left of it.
* **Key**: have the pivotIndex at the 1st pos, **loop and find elm <= pivot, put it at pivotIndex pos (swap)**, then pivotIndex++. Same as put elm before pivotIndex.
* Ex: [4,1,2,3]; pivot=3; [4,1,2] pi=0; Find 1<= 3, swap(4,1); [1,4,2] pi=1, Find 2<=3, swap(4,2); [1,2,4] pi=2; done. Put pivot: 3 into the correct pos, swap(4,3) = [1,2,3,4]; pi is at 2; return 2;
```
int pivot = A[end];
int pivotIndex = start;

for (int hi = start; hi <= end - 1; hi++) {
	if (A[hi] <= pivot) {
		// swap
		int temp = A[pivotIndex];
		A[pivotIndex] = A[hi];
		A[hi] = temp;
		// increment pivotIndex. Finished cur pos.
		pivotIndex++;
	}
}
```
* Time: O(n^2) worst case, O(nlgn) on average.
* Space: O(lgN); since recursive divide to half each time.

Solution 2: Merge Sort
* Use Find mid index, split them and sort[start->mid], then sort[mid+1 -> end].
* After both are sorted, merge them together. [2,3] [1,4] => [1,2,3,4];
* To merge 2 splited arrs, we just need start, mid, and end index. Mid is where we split.
* Create 2 temp arrays, left[start->mid] and right[mid+1 -> end]; 
* *If do it straight from main Array, vals will be overrided* ex: [2,3] [1,4] A[2,3,1,4]; after 1st iter, A[1,3,1,4] [1,3] [1,4] 2 missed.
* Now compare 2 temp arrays, the smaller elms put into position of the main Array cur pos, for ASC order.
* Make sure the temp array with leftovers are filled into the rest of the positions in MainArray.
```
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
```
* Time: O(nlgn)
* Space: O(n); temp arr for every left and right recursive down.


Solution 3: HeapSort;
Ex:
Input data: 4, 10, 3, 5, 1
         4(0)
        /   \
     10(1)   3(2)
    /   \
 5(3)    1(4)

The numbers in bracket represent the indices in the array 
representation of data.

Applying heapify procedure to index 1:
         4(0)
        /   \
    10(1)    3(2)
    /   \
5(3)    1(4)

Applying heapify procedure to index 0:
        10(0)
        /  \
     5(1)  3(2)
    /   \
 4(3)    1(4)
The heapify procedure calls itself recursively to build heap
 in top down manner.
 
* Build a heap from last non-leaf node (idx n/2-1) to root (idx 0); Like above.
* **Key**: heapify always start from bottom level to the top level, so the Max can move up 1 level at a time. If start from top, root don't have access to bottom maxNodes.
* Compare Max(root, Max(leftNode, rightNode)); Swap maxNode with Node.
* If largest is not root, and child swapped with root, we need to heapify the subtree affected. B/c root changed for this subtree. curRoot might be < children.
* ex: [3,5,1,4] 3<->5, [5,3,1,4] 3<->4, [5,4,1,3]; 3 swapped down twice.
* Now the root (idx 0) is the largest. B/c we want ASC order, swap it with the last idx node. So cur Largest is in place.
* Now go find next Largest by maxHeapify the rest of the nodes, without just placed largest (last index); So, heapsize--;
```
for (int i = n - 1; i >= 0; i--) {
	// move cur largest to last index. swap with last node.
	int temp = A[i];
	A[i] = A[0];
	A[0] = temp;
	// removed curLargest, rebuild maxheap with reduced size.
	maxHeapify(A, i, 0);
}
```
* Time: O(nlgn); heapify cost O(h)=O(lgN), we do that n times. n * lgN;
* Space: O(lgN); recursive call to child, == height of tree. O(1) if do it iteratively.

*/

public class SortIntegersII {

	/**
   * @param A: an integer array
   * @return: nothing
   * Solution: QuickSort
   * Time: O(n^2) worst case, O(nlgn) on average.
   * Space: O(lgN); since recursive divide to half each time.
   */
  public void sortIntegers2(int[] A) {
    quickSort(A, 0, A.length - 1);
  }
  
  public void quickSort(int[] A, int start, int end) {
    if (start >= end) {
      return;
    }
    // pivot is in correct position.
    int pivot = partition(A, start, end);
    // sort left and right side of pivot.
    quickSort(A, start, pivot - 1);
    quickSort(A, pivot + 1, end);
  }
  
  public int partition(int[] A, int start, int end) {
    // make endVal = pivot, or any from start -> end.
    int pivot = A[end];
    // pivotIndex is the left pos for <= pivot elm.
    int pivotIndex = start;
    // find elms <= pivot and put to left.
    for (int hi = start; hi <= end - 1; hi++) {
      // if lower val found, swap to left.
      if (A[hi] <= pivot) {
        // swap
        int temp = A[pivotIndex];
        A[pivotIndex] = A[hi];
        A[hi] = temp;
        // increment pivotIndex. Finished cur pos.
        pivotIndex++;
      }
    }
    // now insert pivot to the correct position, left all <= pivot, right > pivot.
    // pivotIndex is at the 1st right position, (1st elm > pivot or pivot itself).
    int temp = A[pivotIndex];
    A[pivotIndex] = A[end];
    A[end] = temp;
    
    return pivotIndex;
  }

  /**
   * @param A: an integer array
   * @return: nothing
   * Solution: mergeSort
   * Time: O(nlgn)
   * Space: O(n); temp arr for every left and right recursive down.
   */
  public void sortIntegers2(int[] A) {
    mergeSort(A, 0, A.length - 1);
  }
  
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

  /**
   * @param A: an integer array
   * @return: nothing
   * Solution: HeapSort
   * Time: O(nlgn); heapify cost O(h)=O(lgN), we do that n times. n * lgN;
	 * Space: O(lgN); recursive call to child, == height of tree. O(1) if do it iteratively.
   */
  public void sortIntegers2(int[] A) {
    heapSort(A);
  }
  
  public void heapSort(int[] A) {
    int n = A.length;
    // maxHeapify all non-leaf nodes. 
    // BinaryTree have parentNodes = totalNodes / 2; 
    // (totalNodes / 2 -1) for 0 based index. From last parent to root.
    for (int i = n / 2 - 1; i >= 0; i--) {
      // creating a maxHeap.
      maxHeapify(A, n, i);
    }
    // after maxHeap created, we extract out root(largest node).
    for (int i = n - 1; i >= 0; i--) {
      // move cur largest to last index. swap with last node.
      int temp = A[i];
      A[i] = A[0];
      A[0] = temp;
      // removed curLargest, rebuild maxheap with reduced size.
      maxHeapify(A, i, 0);
    }
  }
  
  public void maxHeapify(int[] A, int n, int i) {
    // max heapify for pos i as root.
    // init largest as root first.
    int largest = i;
    // 0 base index, parentIdx * 2 + 1 = leftChild Idx.
    // ex: [0,1,2]; 0 * 2 + 1 = 1; [0,1,2,3,4] 1 * 2 + 1= 3;
    int left = i * 2 + 1;
    // right is just left + 1; i * 2 + 2; ex: [0,1,2,3,4] 1 * 2 + 2 = 4;
    int right = i * 2 + 2;
    // root.left != null; check for largest.
    if (left < n && A[left] > A[largest]) {
      largest = left;
    }
    // root.right != null; check for largest.
    if (right < n && A[right] > A[largest]) {
      largest = right;
    }
    // if root not largest.
    if (largest != i) {
      // swap and make root the largest.
      int temp = A[i];
      A[i] = A[largest];
      A[largest] = temp;
      // root i val becomes child, subtree may be affected, 
      // need to maxHeapify the subtree.
      // ex: [3,5,1,4] 3<->5, [5,3,1,4] 3<->4, [5,4,1,3];
      maxHeapify(A, n, largest);
    }
  }
    
}