/**
324. Wiggle Sort II
Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

Example 1:

Input: nums = [1, 5, 1, 1, 6, 4]
Output: One possible answer is [1, 4, 1, 5, 1, 6].
Example 2:

Input: nums = [1, 3, 2, 2, 3, 1]
Output: One possible answer is [2, 3, 1, 3, 1, 2].
Note:
You may assume all input has valid answer.

Follow Up:
Can you do it in O(n) time and/or in-place with O(1) extra space?
*/

/*
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
*/

class WiggleSortII {

	/**
	 * Solution 2 : brute force, sort, and then copy array to assign odds and evevns
	 * time: O(nlgn)
	 * Space: O(n)
	 */
	public void wiggleSort(int[] nums) {
    int size = nums.length;
    // right 1st pos
    int mid = (size + 1) / 2;
    int[] copy = Arrays.copyOf(nums, size);
    Arrays.sort(copy);
    // right half insert even pos;  either = odds or odds + 1.
    // [1,1,1,1,4,5,6] = odd + 1; [1,1,1,3,4,5] = odd;
    for (int i = mid - 1, even = 0; i >= 0; i--, even += 2) {
      nums[even] = copy[i];
    }
    // left half insert odd pos.
    for (int i = size - 1, odd = 1; i >= mid; i--, odd += 2) {
      nums[odd] = copy[i];
    }
  }

	/**
	 * Solution 1: find median, use indexMapping, odds with larger, evens with smaller.
	 * Time: O(n) average; O(n^2) worse case, mid is last index to partition.
	 * space: O(1);
	 */
  public void wiggleSort(int[] nums) {
    if (nums == null || nums.length == 0) {
      return;
    }
    int size = nums.length;
    // kth largest Desc, so size+1, ex: size 7, 7->1 mid=4th largest; 7/2 = 3 no, (7+1)/2 = 4;
    int mid = (size + 1) / 2;
    // partition and find median.
    int median = findKthLargest(nums, mid, 0, size - 1);
    // now all larger nums on left, all smaller nums on right. Partly sorted.
    int oddPos = 0;
    int i = 0;
    int evenPos = size - 1;
    // 3 way partition.
    while (i <= evenPos) {
      // > median put in odd positions 
      if (nums[mapIndex(i, size)] > median) {
        swap(nums, mapIndex(i, size), mapIndex(oddPos, size));
        i++;
        oddPos++;
      } else if (nums[mapIndex(i, size)] < median) {
        // < median, put in even positions.
        swap(nums, mapIndex(i, size), mapIndex(evenPos, size));
        evenPos--;
      } else {
        // num == median, ignore
        // if all big in evens, all small in odds, then the mid is taking care of.
        i++;
      }
    }
  }
  
  public int mapIndex(int i, int size) {
    // for 1st half
    // 0->1, 1->3, 2->5 Odds indexes (2 * i + 1);
    if (i < size / 2) {
      return 2 * i + 1;
    }
    // 2nd half
    // size odd 7: 3->0, 4->2, 5->4, 6->6; (2 * i + 1) % size;
    // (2 * i + 1) is basically the (i+1)th odd num.
    if (size % 2 != 0) {
      return (2 * i + 1) % size;
    } else {
      // size even 6: 3->0, 4->2, 5->4; (2 * i + 1) % (size + 1);
      return (2 * i + 1) % (size + 1);
    }
  }
  
  public int findKthLargest(int[] nums, int k, int start, int end) {
    if (start > end) {
      return -1;
    }
    int pivotIndex = partitionDesc(nums, start, end);
    // (pIdx-start) < k-1, means target is smaller, on the right.
    // (pIdx-start) we need to covert to pos in 0->len-1;
    if (k - 1 > pivotIndex - start) {
      // update k, larger elms elimated on left include pivot.
      k = k - (pivotIndex - start + 1);
      return findKthLargest(nums, k, pivotIndex + 1, end);
    } else if (k - 1 < pivotIndex - start) {
      // target is larger, on the left.
      // same k, only smaller # is eliminated, still kth largest.
      return findKthLargest(nums, k, start, pivotIndex - 1);
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