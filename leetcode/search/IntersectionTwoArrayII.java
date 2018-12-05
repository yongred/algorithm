
/**
Solution 1: sort both array, compare number using 2 pointers.
How to arrive:
* Brute force is to iterates through n1 and check every elms if in n2, takes O(n * m) time.
* To optimize, we can sort both arrays, and compare them using 2 pointers in ASC order.
* Increment index1 if n1 < n2, index2 if n2 < n1; both if n1 == n2;
* Ex: [1,2,3,3,4] [1,3,3,3,5] 1==1 [1] both++; 2<3, i1++; 3=3 [1,3] both++; 3=3 [1,3,3] both++; 4 > 3 j++; 4 <5 i++; Done. [1,3,3] is ANS.
* Time: O(nlgn + mlgm);
* Space: O(n); len of smaller arr at worst.

Solution 2: Using hashMap, store n1 vals and count.
How to arrive:
* Different from First question, we need the fewest appearance of chars out of 2 arr.
* We can do that using a HashMap to store n1 nums as key and count the nums as val. Hash(num, count);
* Then we iterates n2 and check if n2 nums in n1 hash, also check if n2 cur val appears more than n1. If not add to res list. By using decrement in hash count. When hash count = 0; then don't add anymore cur val.
```
// if exist in both array and n2 cur val not more than n1.
      if (hash.containsKey(nums2[i]) && hash.get(nums2[i]) > 0) {
        list.add(nums2[i]);
        // 1 less cur val in nums1;
        hash.put(nums2[i], hash.get(nums2[i]) - 1);
      }
```
* Time: O(n + m);
* Space: O(n); n or m which ever is smaller. At worst case smaller len is the space.

Follow up questions:
1. What if the given array is already sorted? How would you optimize your algorithm?
* If sorted just use 2 pointers algorithm like solution 1.

2. What if nums1's size is small compared to nums2's size? Which algorithm is better?
* Use nums1 to store in hashMap will optimize space complexity, since n1 have smaller size hash.

3. What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
* If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the memory, and record the intersections. (split multiple times to get data from n2), Ex: 1->100 first, then 100->200 ..
* If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external sort), then read 2 elements from each array at a time in memory, record intersections.

*/

class IntersectionTwoArrayII {

	/**
	 * Solution: Using hashMap, store n1 vals and count.
	 * Time: O(n + m);
	 * Space: O(n)
	 */
	public int[] intersect(int[] nums1, int[] nums2) {
    // val and count for nums1.
    HashMap<Integer, Integer> hash = new HashMap<>();
    // put nums1 into hashMap
    for (int num : nums1) {
      if (hash.containsKey(num)) {
        hash.put(num, hash.get(num) + 1);
      } else {
        hash.put(num, 1);
      }
    }
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < nums2.length; i++) {
      // if exist in both array and n2 cur val not more than n1.
      if (hash.containsKey(nums2[i]) && hash.get(nums2[i]) > 0) {
        list.add(nums2[i]);
        // 1 less cur val in nums1;
        hash.put(nums2[i], hash.get(nums2[i]) - 1);
      }
    }
    // convert list to array.
    int[] res = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      res[i] = list.get(i);
    }
    
    return res;
  }

	/**
	 * Solution: Sort both arrs, and use 2 pointers to find intersects.
	 * Time: O(mlgm + nlgn)
	 * Space: O(n);
	 */
  public int[] intersect(int[] nums1, int[] nums2) {
    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    ArrayList<Integer> list = new ArrayList<>();
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
        list.add(nums1[i]);
        // increment both
        i++;
        j++;
      }
    }
    // put list res in array.
    int[] res = new int[list.size()];
    int index = 0;
    for (int num : list) {
      res[index] = num;
      index++;
    }
    return res;
  }
}