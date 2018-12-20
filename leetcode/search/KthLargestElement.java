/**
215. Kth Largest Element in an Array
Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example 1:

Input: [3,2,1,5,6,4] and k = 2
Output: 5
Example 2:

Input: [3,2,3,1,2,4,5,5,6] and k = 4
Output: 4
Note: 
You may assume k is always valid, 1 ≤ k ≤ array's length.
*/

/*
[3,2,1,5,6,4] k = 4;
k=4;
parti 4: [5,6,4,3,2,1]; pIdx 2; 2 < k-1 (3); go right part [3,2,1].
k=1; k updated to 4-3=1, because 3 larger elms elimated.
parti 1: [3,2,1] already in place: pIdx=2; 2 > k-1 (0); go left [3,2]
k=1 same, because we still finding 1st largest, only smaller # is eliminated.
parti 2: [3,2] already in place: pIdx=1; 1 > k-1 (0); go left [3]
k=1 same;
parti 3: [3] PIdx=0; 0 == k-1 (0); find our number. ANS= 3;
*/

/**
Solution 1: partition, compare k with partIndex, decide search left or right.
How to arrive:
* Since we are looking for kth largest, we can partition the arr in DESC order.
* when we find partIndex == k-1, we know this partition num is the answer.
* When we partition, we put all larger nums on left, smaller on right.
* So, comparing k to pivotIndex:
	* k-1 < pIdx: we know target is larger, so we go look in LeftSide.
	* k-1 > pIdx: we know target is smaller, so we go look in rightSide.
	* k-1 = PIdx: we find our num;
	* **Key**: k-1 is comparing to (pIdx - start) if we passing by (start, end) indexes rather than subarray.
* Time: O(n) on average; O(n^2) worse, quickSort worse case target is the last one to be pivot.
	ex: arr is sorted decrement, 10-1, and k is find 1st largest.
* Space: O(lgN) in worse case.
*/

/**
Solution 1: find median, use indexMapping, odds with larger, evens with smaller.
[5,3,4,1,2,6];
parti median 4: [6,5,4,3,2,1]
Now we want [6,5,4] in odd pos: 1,3,5;
we want [3,2,1] in even pos: 0,2,4;
Use a map index: 
i[0,1,2,3,4,5]
n[1,5,2,6,3,4]
--------------
m[i1,i3,i5,i0,i2,i4]
              l
                  i
              r
n[1]=5 > 4 swap(i1 n5, l1 n5) l++ i++; n[3]=3 < 4 swap(i3 n3, r4 n2) r--;
n[3]=2 < 4 swap(i3 n2, r2 n4) r--; n[3]=4 = 4, i++;
n[5]=1 < 4 swap(i5 n1, r0 n6) r--; n[5]=6 > 4 swap(i5 n6, l3 n4) i++ l++;
i > r Done;

How to arrive:
* If we have know all the largest half and smaller half of the arr, then we can just put all larger nums in odd positions, and smaller nums in even positions.
Ex: [6,5,4,3,2,1]
we want [6,5,4] in odd pos: 1,3,5;
we want [3,2,1] in even pos: 0,2,4;
res: [3,6,2,5,4,1];
* We can either sort arr or Partition to find the median. Partition is more effecient, we don't need to sort the whole thing.
* To find the median, we do kthLargetNum( (size+1)/2 );
* It is in Leetcode 215, basically partition, decide search left or right by partitionIndex.
* When we know the median, we can compare nums to median;
	* if num < median, we put in even positions;
	* if num > median, we put in odd positions;
	* if num == median, we ignore, b/c if all big in evens, all small in odds, then the mid is taking care of.
* Easier way to put them in correct positions is using a Copy array: Space: cost O(n);
Ex: 
copy:[4,6,5,1,3,2]
res:   [1,4,3,6,2,5];
* Or for space: O(1): we can find out the formula for mapping indexes:
Ex: 
i[0,1,2,3,4,5]
n[1,5,2,6,3,4]
m[1,3,5,0,2,4]; Then n[m[i]] = val of oddPos or evenPos.
* for 1st half: 0->1, 1->3, 2->5, Odds = indexes (2 * i + 1);
* (2 * i + 1) is basically the (i+1)th odd num.
* for 2nd half: we need to find out the size is even or odd:
	* size odd 7: 3->0, 4->2, 5->4, 6->6; (2 * i + 1) % size;
	* size even 6: 3->0, 4->2, 5->4; (2 * i + 1) % (size + 1);
* Now we just need to do the 3-way partition, smaller on even pos, larger on odds, mid ignore.
* Time: O(n) on average; O(n^2) worse, quickSort worse case target is the last one to be pivot.
	ex: arr is sorted decrement, 10-1, and k is find 1st largest.
* Space: O(lgN) in worse case.
*/

class KthLargestElement {

	/**
	Solution 1: partition, compare k with partIndex, decide search left or right.
	Time: O(n) on average; O(n^2) worse, quickSort worse case target is the last one to be pivot.
	ex: arr is sorted decrement, 10-1, and k is find 1st largest.
	Space: O(lgN) in worse case.
	*/
  public int findKthLargest(int[] nums, int k) {
    return helper(nums, k, 0, nums.length - 1);
  }
  
  public int helper(int[] nums, int k, int start, int end) {
    if (start > end) {
      return -1;
    }
    int pivotIndex = partitionDesc(nums, start, end);
    // (pIdx-start) < k-1, means target is smaller, on the right.
    // (pIdx-start) we need to covert to pos in 0->len-1;
    if (k - 1 > pivotIndex - start) {
      // update k, larger elms elimated on left include pivot.
      k = k - (pivotIndex - start + 1);
      return helper(nums, k, pivotIndex + 1, end);
    } else if (k - 1 < pivotIndex - start) {
      // target is larger, on the left.
      // same k, only smaller # is eliminated, still kth largest.
      return helper(nums, k, start, pivotIndex - 1);
    } else {
      // (pIdx-start) == k-1; we found our target.
      return nums[pivotIndex];
    }
  }
  
  public int partitionDesc(int[] nums, int start, int end) {
    int pivotVal = nums[end];
    int partIndex = start;
    // loop start to end
    for (int i = start; i < end; i++) {
      // find > pVal.
      // for == cases, we want the partIndex to be in the front most.
      // ex: 3,3,2,1; pVal=3, partIndex = 0;
      if (nums[i] > pivotVal) {
        // swap with partIndex val
        swap(nums, partIndex, i);
        // increment partIndex, put this larger num in place, find next.
        partIndex++;
      }
    }
    // now, last pos left. It is for end pivotVal.
    swap(nums, partIndex, end);
    return partIndex;
  }
  
  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}