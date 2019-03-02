/**
169. Majority Element

Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2
*/

/*
Solution 1: HashMap, count the elm apearances. return when > n/2;

* Simply count very elm's appearance, when elm # > n/2; return that elm
* Time: O(n)
* Space: O(n)

-----------

Solution 2:  Sort, and get middle index num.
Ex: [2,0,1,2,2,1,2]
sorted: [0,1,1,2,2,2,2]; the middle index will always be the elm > n/2;
Ex: [0,0,1,0,0,2]
sorted: [0,0,0,0,1,2]; return 0; mid index 6/2=3; Since there is always an elm > n/2;
* Time: O(nlgn)
* Space: O(1)

---------

Solution 3: Boyer-Moore Voting Algorithm
Ex: [2,2,1,1,1,2,2]
cand: 2-, 1-, 2 DONE. ANS = 2;
count2: i0 +1, i1 +1(2), i2 -1(1), i3 -1(0), i6 +1(1)
count1: i4 +1, i5 -1(0)

* If we count an elm: +1 everytime it shows, and -1 everytime it not shows, we will always have a count >= 1 if this elm # > n/2;
Ex: [2,2,1,1,2]; #of 2 = 3; #of !2 = 2;  3-2= 1;
* When count == 0; that means we cancel out cand #s. Assign next num as new cand.
* B/c question says there is always a elm > n/2; The cand last remain will be our ans.
* Time : O(n)
* Space: O(1)

*/

class MajorityElement {

	/**
	 * Solution 1: HashMap, count the elm apearances. return when > n/2;
	 * Time: O(n)
	 * Space: O(n)
	 */
  public int majorityElement(int[] nums) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) {
      count.put(num, count.getOrDefault(num, 0) + 1);
      if (count.get(num) > nums.length / 2) {
        return num;
      }
    }
    // should never reach here is there is elm > n/2;
    return -1;
  }

  /**
   * Solution 2: Sort, and get middle index num.
   * Time: O(nlgn)
   * Space: O(1)
   */
  public int majorityElement(int[] nums) {
    Arrays.sort(nums);
    // if elm # > n/2; then in n/2 index, it will always be this elm;
    return nums[nums.length / 2];
  }


  /**
  Solution 3: Boyer-Moore Voting Algorithm
	* Time : O(n)
	* Space: O(1)
	*/
  public int majorityElement(int[] nums) {
    int count = 0;
    int cand = -1;
    for (int num : nums) {
      // cur cand been cancelled out. Assign curNum as new cand.
      if (count == 0) {
        cand = num;
      }
      // if match cur cand then +1, not match -1.
      count = (cand == num) ? count + 1 : count - 1;
    }
    // since there must be a num > n/2; The cand left is the ans.
    return cand;
  }

  
}