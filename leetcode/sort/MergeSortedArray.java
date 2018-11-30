/**
88. Merge Sorted Array
Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
*/

/**
Solution: Fill in nums1 backward, DESC.
nums1 = [3,5,6,0,0,0], m = 3
nums2 = [2,3,4],       n = 3
[3,5,6,0,0,0] => [3,5,-,0,0,6] => [3,-, -, 0,5,6] => [3,-, -, 4,5,6] => [-, -,3,4,5,6] => [-, 3,3,4,5,6] 
=> [2, 3,3,4,5,6];
How to arrive:
* Problem: have to push nums1 elms 1 pos if nums2 < nums1 if compare ASC.
* But since nums1 have len = m + n, then we can fill out nums1's pos backward, without having to push nums1 elms.
* Loop DESC, and fill out by compare nums1 and nums2, which one is greater.
* Remove nums1[i] (assign to MIN_VALUE) when place nums1 to curPos, since nums1[i] is used, remove from i pos. So it does'n compared again.
```
while (i >= 0 && j >= 0) {
	if (nums1[i] > nums2[j]) {
		nums1[i + j + 1] = nums1[i];
		nums1[i] = Integer.MIN_VALUE;
		i--;
	} else {
		nums1[i + j + 1] = nums2[j];
		j--;
	}
}
```
* If there is nums2 elms left. Meaning all nums1 is filled. Then the rest positions are all nums2.
```
while (j >= 0) {
      nums1[j] = nums2[j];
      j--;
    }
```
* Don't have to do that if nums1 elms left, since it is already in place.
* Time: O(m + n);
* Space: O(1);

*/

class MergeSortedArray {

  public void merge(int[] nums1, int m, int[] nums2, int n) {
    if (n == 0) {
      return;
    }
    
    int i = m - 1;
    int j = n - 1;
    // compare nums1 to nums2 cur largest and placed in nums1 from n+m->0 pos; DESC.
    while (i >= 0 && j >= 0) {
      if (nums1[i] > nums2[j]) {
        nums1[i + j + 1] = nums1[i];
        nums1[i] = Integer.MIN_VALUE;
        i--;
      } else {
        nums1[i + j + 1] = nums2[j];
        j--;
      }
    }
    // no more nums1, rest is all nums2.
    while (j >= 0) {
      nums1[j] = nums2[j];
      j--;
    }
  }
}