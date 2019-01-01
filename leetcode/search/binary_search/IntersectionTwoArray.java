
/**
Solution 1: sort both array, compare number using 2 pointers.
How to arrive:
* Brute force is to iterates through n1 and check every elms if in n2, takes O(n * m) time.
* To optimize, we can sort both arrays, and compare them using 2 pointers in ASC order.
* Increment index1 if n1 < n2, index2 if n2 < n1; both if n1 == n2;
* Ex: [1,2,3,3,4] [2,4,5] index1: 1->2, index2: 2; i1->3, i2->4; i1->4;
* Duplicates case: [3,3,3,5,7] [2,3,3,4,5]
	* To handle duplicate cases, we can just use a HashSet. Or add a checking condition to prev elm.
	* if (i > 0 && nums1[i] == nums1[i - 1] || j > 0 && nums2[j] == nums2[j - 1])
* Time: O(nlgn + mlgm);
* Space: O(n); len of smaller arr at worst.

Solution 2: Sort smaller array, binary search larger arr #s in smallerArr.
How to arrive:
* We can just sort n1, then binary search n2 elms in n1.
* Again, use a HashSet to handle duplicates.
* Time: O( min(mLogm + nLogm, nLogn + mLogn) );  Sort the smaller arr mLgm, then search nLgm;
* Space: O(min(n, m)); res len worst case is len of smaller arr.

Solution 3: Use 2 hashs, 1 for nums1, another for intersects res.
How to arrive:
* The brute force solution, checking each elms in another array, takes O(n);
* If we use a Hash, it will only take O(1);
* So we put nums1 in Hash, check nums2 elms in nums1 hash.
* If exists in n1 hash, add to HashSet intersects to prevent duplicates.
* Time: O(n + m)
* Space: O(n);

*/

class IntersectionTwoArray {

	/**
	 * Solution: using hashsets, 1 for nums1, another for intersects res.
	 * Time: O(n + m)
	 * Space: O(n);
	 */
	public int[] intersection(int[] nums1, int[] nums2) {
    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    // use 2 hash to check if elm exits in other array.
    HashSet<Integer> set1 = new HashSet<>();
    HashSet<Integer> intersects = new HashSet<>();
    // store nums1 in set1.
    for (int num : nums1) {
      set1.add(num);
    }
    // check nums2 elms in num1 hashset.
    for (int num : nums2) {
      if (set1.contains(num)) {
        // add intersections.
        intersects.add(num);
      }
    }
    // convert to array.
    int[] res = new int[intersects.size()];
    int index = 0;
    for (int num : intersects) {
      res[index] = num;
      index++;
    }
    
    return res;
  }

	/**
	 * Solution: sort smaller array, binary search larger arr #s in smallerArr.
	 * Time: O( min(mLogm + nLogm, nLogn + mLogn) ); Sort the smaller arr mLgm, then search nLgm;
	 * Space: O(min(n, m)); res len worst case is len of smaller arr.
	 */
  public int[] intersection(int[] nums1, int[] nums2) {
    // use hashSet for unique #s.
    HashSet<Integer> set = new HashSet<>();
    // make sure nums1 <= nums2.
    if (nums1.length > nums2.length) {
      int[] temp = nums1;
      nums1 = nums2;
      nums2 = temp;
    }
    // sort the smaller arr.
    Arrays.sort(nums1);
    // binary search each larget array #s, if in smaller array, add to resSet.
    for (int i = 0; i < nums2.length; i++) {
      if (binarySearch(nums1, nums2[i])) {
        set.add(nums2[i]);
      }
    }
    // copy res to array.
    int[] res = new int[set.size()];
    int i = 0;
    for (int num : set) {
      res[i] = num;
      i++;
    }
    return res;
  }
  
  public boolean binarySearch(int[] nums, int target) {
    if (nums.length == 0) {
      return false;
    }
    int start = 0;
    int end = nums.length - 1;
    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (nums[mid] == target) {
        return true;
      } else if (nums[mid] > target) {
        // tar on left.
        end = mid - 1;
      } else {
        // midVal < tar, on right
        start = mid + 1;
      }
    }
    return false;
  }

  /**
   * Solution: sort both arrays, use 2 pointers check elms
   * Time: O(nlgn + mlgm);
   * Space: O(n); len of smaller arr at worst.
   */
  public int[] intersection(int[] nums1, int[] nums2) {
    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    HashSet<Integer> set = new HashSet<>();
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0;
    int j = 0;
    while (i < nums1.length && j < nums2.length) {
      // smaller one iterates. B/c sorted ASC, larger #s on right.
      if (nums1[i] < nums2[j]) {
        // n1 smaller.
        i++;
      } else if (nums2[j] < nums1[i]) {
        // n2 smaller
        j++;
      } else {
        // n1 == n2; add. Using hashSet, no need additional check duplicates.
        // if (i > 0 && nums1[i] == nums1[i - 1] || j > 0 && nums2[j] == nums2[j - 1]) {
        //   i++;
        //   j++;
        //   continue;
        // }
        set.add(nums1[i]);
        // increment both
        i++;
        j++;
      }
    }
    // put set res in array.
    int[] res = new int[set.size()];
    int index = 0;
    for (int num : set) {
      res[index] = num;
      index++;
    }
    return res;
  }

}