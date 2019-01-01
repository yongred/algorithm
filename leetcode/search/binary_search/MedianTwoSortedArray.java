/**
4. Median of Two Sorted Arrays
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
*/

/**
Solution: Use partition and binary search.
Ex: n1 = [4,8,9,10]  n2 = [1,2,3,5,6,7];
Left: n1L [4,8],  n2L [1,2,3]; Right: n1R [9,10], n2R [5,6,7];
n1L (8) > (5) n2R; Conclude n1R #s > 5 as well, cut them out of possible medians.
n1 => [4,8]
Left: n1L [4], n2L [1,2,3,5]; Right: n1R [8], n2R [6,7]
n1L 4 < 6 n2R; correct. n2L (5) < (8) n1R; correct;
Find the 4 nums that is possible for medians. Left(4,5) Right(8,6);
n1 + n2 = 10 is even: Max(4,5) = 5; Min(8,6) = 6; (5+6)/2 = 5.5; 
ANS = 5.5;

* To find the median, we need to have smaller #s on left and larger #s on right. If Odd, pick the middle, if Even (Max(left) + Min(right) ) / 2;
* We don't know the exact mid, since it's 2 arrays. But we can divide 2 arrays into smaller half and larger half:
	* Ex:  Left: n1 [4,8]  n2 [1,2,3]; Right: n1 [9,10], n2 [5,6,7];
* We need to partition untill **All elms in left <= elms in right**. 
* For that we just need to check n1L last elm <= n2R 1st elm; And n1R 1st elm > n2L last elm.
* In this case, Left: n1 [4,8]  n2 [1,2,3]; Right: n1 [9,10], n2 [5,6,7]; 8 > 5; 
* n1 [9,10] can be eliminated b/c (5 < 8) & n1R > n1L; 
* if n1R < n2L then, n1L #s is eliminated.
* When n1L < n2R && n1R > n2L; We have pivot all small #s to left and largers to right.
* Now we can calc base on Even or Odd.
* If Odd, pick the return Max(left);
* if Even (Max(left) + Min(right) ) / 2;
* Time: O(lg (Min(m,n)); lg of shorter arr.
* Space: O(1);

-----Easier understandable solution---
Solution: Recursive, using mid1 & mid2 as boundaries. Check k on right or left.
"""
                                     k on right half             k on left half
                               ___________________________ ____________________________
                              |                           |                            |
mid of nums1 > mid of nums2   | eliminate left half nums2 | eliminate right half nums1 |
                              |___________________________|____________________________|
                              |                           |                            |
mid of nums1 <= mid of nums2  | eliminate left half nums1 | eliminate right half num2  |
                              |___________________________|____________________________|
... (mid of nums1) ...|... (mid of nums2) ...
                      ^
                     mid
... (mid of nums1) ...|... (mid of nums2) ...
                      ^
                     mid
"""
* We can find the mids for n1 and n2. So we have 4 parts: n1L, n2L, n1R, n2R;
* Same idea as partition, but this time using mids as partition.
* To check which part to eliminates, we can see k is on the right or left side, as the graph show above.
	... (mid of nums1) ...|... (mid of nums2) ...
* k > (mid1 + mid2) right side, k <= (mid1 + mid2) on left side.
* k on right side means, we can elimate 1 of the left part. The smallest left part.
	* To find smallest left part: compare n1[mid1] & n2[mid2]; the smaller one's left part gets eliminated.
	* **Key**: don't forget to update k, since index < k elms is eliminated. (k - eliminatedSize);
* k on the left side, we can elimate 1 of the right part. The largest right part.
	* Compare n1[mid1] & n2[mid2], this time the larger one's right part is eliminated.
	* k stays the same, since elms index eliminated is > k.
* Time: O(log(min(m,n)));
* Space: O(log(min(m,n)));
*/

class MedianTwoSortedArray {

	/**
	 * Solution: iterative, Partition.
	 * Time: O(lg (Min(m,n)); lg of shorter arr.
	 * Space: O(1);
	 */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int n1 = nums1.length;
		int n2 = nums2.length;
		if (n1 == 0 && n2 == 0) {
			return 0.0;
		}
		// ensure n1 <= n2; Switch arr if not. Make nums1 the shorter one.
		if (n1 > n2) {
			int[] temp = nums1;
			nums1 = nums2;
			nums2 = temp;
			// len switch.
			int tempLen = n1;
			n1 = n2;
			n2 = tempLen;
		}
		// if smaller arr is empty. Return mid for the other arr.
		if (n1 == 0) {
			int mid = (n2 - 1) / 2;
			return (n2 % 2 == 0) ? (nums2[mid] + nums2[mid + 1]) / 2.0 : nums2[mid];
		}
		// use to cut off not possible #s.
		int minN1 = 0;
		int maxN1 = n1;
		// 1st index in right, half of the elm #.
		int halfLen = (n1 + n2 + 1) / 2;
		while (minN1 <= maxN1) {
			// partition n1. Find cur mid.
			int pn1 = (minN1 + maxN1) / 2;
			// pn1 is the # of left elms in p1, 2nd partition w/o it.
			int pn2 = halfLen - pn1;
			// if n1 left elm > n2 right elm, then n1 right elms > n2 as well.
			if (pn1 > minN1 && nums1[pn1 - 1] > nums2[pn2]) {
				// cut off n1 right elms.
				maxN1 = pn1 - 1;
			} else if (pn1 < maxN1 && nums1[pn1] < nums2[pn2 - 1]) {
				// check right n1 < left n2; then n1 right too small. Also n1 left elms cut off.
				minN1 = pn1 + 1;
			} else {
				// Find the 4 nums we need.
				double leftNum = 0.0;
				if (pn1 <= 0) {
					leftNum = nums2[pn2 - 1];
				} else if (pn2 <= 0) {
					leftNum = nums1[pn1 - 1];
				} else {
					leftNum = Math.max(nums1[pn1 - 1], nums2[pn2 - 1]);
				}
				// odd median, 1 num, leftMost, largest from left side.
				if ((n1 + n2) % 2 != 0) {
					return leftNum;
				}

				double rightNum = 0.0;
				if (pn1 >= n1) {
					rightNum = nums2[pn2];
				} else if (pn2 >= n2) {
					rightNum = nums1[pn1];
				} else {
					rightNum = Math.min(nums1[pn1], nums2[pn2]);
				}
				// even median, (maxLeft + rightMin) / 2;
				return (leftNum + rightNum) / 2.0;
			}
		}

		return 0.0;
	}


	/**
	 * Solution: recursive, using mid1 & mid2 as boundaries.
	 * Time: O(log(min(m,n)));
	 * Space: O(log(min(m,n)));
	 */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int total = nums1.length + nums2.length;
    // even
    if (total % 2 == 0) {
      return (findKth(nums1, nums2, total / 2, 0, nums1.length, 0, nums2.length)
          + findKth(nums1, nums2, total / 2 - 1, 0, nums1.length, 0, nums2.length)) / 2.0;
    }
    // odd
    return findKth(nums1, nums2, total / 2, 0, nums1.length, 0, nums2.length);
  }
  
  public double findKth(int[] nums1, int[] nums2, int k,
          int start1, int end1, int start2, int end2) {
    // only 1 arr have size. end is the size. Exclusive.
    if (start1 >= end1) {
      return nums2[start2 + k];
    }
    if (start2 >= end2) {
      return nums1[start1 + k];
    }
    // mid = right 1st elm.
    int mid1 = (start1 + end1) / 2;
    int mid2 = (start2 + end2) / 2;
    // k on the right, > mid1 + mid2;
    if (k > (mid1 - start1) + (mid2 - start2)) {
      // we can cut off one of left side.
      if (nums1[mid1] > nums2[mid2]) {
        // cut off nums2 left.
        int newStart2 = mid2 + 1;
        // left n2 elms removed, update k index. (mid2 - start2) is # of n2 left elms.
        int newk = k - (mid2 - start2) - 1;
        return findKth(nums1, nums2, newk, start1, end1, newStart2, end2);
      } else {
        // cut off nums1 left
        int newStart1 = mid1 + 1;
        // left n1 elms removed, update k index.
        int newk = k - (mid1 - start1) - 1;
        return findKth(nums1, nums2, newk, newStart1, end1, start2, end2);
      }
    } else {
      // k on the left side, <= mid1 + mid2;
      // we can cut off right side
      if (nums1[mid1] < nums2[mid2]) {
        // cut off nums2 right side
        // k remains the same, since index > k get cut off.
        return findKth(nums1, nums2, k, start1, end1, start2, mid2);
      } else {
        // cut off nums1 right side, end is exclusive, no need for end-1;
        return findKth(nums1, nums2, k, start1, mid1, start2, end2);
      }
    }
  }

}