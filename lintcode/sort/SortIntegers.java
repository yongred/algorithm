/**
463. Sort Integers
Given an integer array, sort it in ascending order. Use selection sort, bubble sort, insertion sort or any O(n2) algorithm.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
*/

/**
Solution 1: Bubble Sort;
Ex: [6,5,7,3,2] => 6>5 [5,6,7,3,2], 6<7 no change, 7>3 [5,6,3,7,2], 7>2 [5,6,3,2,7]; 7 in place (last pos); Continue again with [5,6,3,2] w/o [7];
* Bubble sort swap 2 adj nums if cur < next. Then iter to next 2 pos. 
* After each traverse of all elms, largest elm is in place. So we can eliminates largest elm for the next traverse.
```
for (int i = 0; i < A.length - 1; i++) {
      // bubble sort put largest elm in place 1st, 
      // which means after i iters, i largest elms are in place.
      for (int j = 0; j < A.length - i - 1; j++) {
```
* traverse n times, each time eliminates already placed positions. i traversal means last i places are placed.
* Time: O(n^2);
* Space: O(1)

----------
Solution 2: Insertion Sort
Ex: [3,2,1] => [3| 2,1]; [3,2|1] cur=2, 3>2 -> [N,3|1] -> [2,3|1]; [2,3,1] cur=1; 3>1 -> [2,N,3], 2>1 -> [N,2,3], [1,2,3];
* Like Playing Poker, each elm is like a new Card drawed into your hand, and you Insert that card to the right place.
* Ex: [10, Q, K, A] in your hand cards,  you draw a 'J'; Now you move over Q,K,A to the right and place J in. [10,J,Q,K,A];
* So, we iterates nums 1 by 1, representing new vals draw into hand. For loop
* Then, we compare NewVal with prev vals, move all prev vals > newVal to the right. Using a while loop. Stop until preVal <= newVal.
* Place the newVal there.
```
while (prev >= 0 && curVal < A[prev]) {
	// move 1 pos right to leave room for placing cur val.
	A[prev + 1] = A[prev];
	// go to next prev.
	prev--;
}
// place curVal in the right place.
// prev is at either <= curVal or -1.
A[prev + 1] = curVal;
```
* Time: O(n^2)
* Space: O(1)

---------------
Solution 3: Selection Sort.
Ex: [|4,2,1,3,5] => min find 1, [1,|2,4,3,5] => min find 2, in place no change; [1,2,|4,3,5] min=3 => [1,2,|3,4,5]; [1,2,3,|4,5] good; [1,2,3,4,|5] good. DONE.
* Find the Min for the 1st pos, then find min for the 2nd pos, then 3rd and so on...
* Each traverse will put the next Min in place.
* We need a loop to represent all already sorted positions. Put a boundary after those pos so it doesn't traverse those again.
* Find the next Min in the rest of the elms. Place it to the cur pos.
```
// each iter brings 1 smallest pos to sorted, so no need to iter last one.
for (int i = 0; i < A.length - 1; i++) {
	int minIndex = i;
	// find the smallest val index.
	for (int j = i; j < A.length; j++) {
		if (A[j] < A[minIndex]) {
			minIndex = j;
		}
	}
	// place min in cur pos. Swap with minIndex val.
	int temp = A[i];
	A[i] = A[minIndex];
	A[minIndex] = temp;
}
```
* Time: O(n^2)
* Space: O(1)
*/

public class SortIntegers {

  /**
   * @param A: an integer array
   * @return: nothing
   */
  public void sortIntegers(int[] A) {
  	bubbleSort(A);
  }

  /**
   * Solution: Selection Sort
   * Time: O(n^2)
   * Space: O(1)
   */
  public void selectionSort(int[] A) {
    // each iter brings 1 smallest pos to sorted, so no need to iter last one.
    for (int i = 0; i < A.length - 1; i++) {
      int minIndex = i;
      // find the smallest val index.
      for (int j = i; j < A.length; j++) {
        if (A[j] < A[minIndex]) {
          minIndex = j;
        }
      }
      // place min in cur pos. Swap with minIndex val.
      int temp = A[i];
      A[i] = A[minIndex];
      A[minIndex] = temp;
    }
  }

  /**
   * Solution: Bubble Sort
   * Time: O(n^2)
   * Space: O(1)
   */
  public void bubbleSort(int[] A) {
  	// after n-1 traverse, all larger elms are placed correctly
  	// so smallest elm is also in the 1st index.
    for (int i = 0; i < A.length - 1; i++) {
      // bubble sort put largest elm in place 1st, 
      // which means after i iters, i largest elms are in place.
      for (int j = 0; j < A.length - i - 1; j++) {
        // compare cur and next, swap if next < cur.
        if (A[j + 1] < A[j]) {
          int temp = A[j];
          A[j] = A[j + 1];
          A[j + 1] = temp;
        }
      }
    }
  }

  /**
   * Solution: Insertion Sort
   * Time: O(n^2)
   * Space: O(1)
   */
  public void insertionSort(int[] A) {
    for (int i = 1; i < A.length; i++) {
      int curVal = A[i];
      int prev = i - 1;
      // search for the right pos to insert cur. 
      // Anything > cur, move to right.
      while (prev >= 0 && curVal < A[prev]) {
        // move 1 pos right to leave room for placing cur val.
        A[prev + 1] = A[prev];
        // go to next prev.
        prev--;
      }
      // place curVal in the right place.
      // prev is at either <= curVal or -1.
      A[prev + 1] = curVal;
    }
  }


}