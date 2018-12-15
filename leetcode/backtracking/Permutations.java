/**
Given a collection of distinct integers, return all possible permutations.

Example:

Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
*/

/**
Solution: backtracking, recursive and loop unchoosed nums.
Ex: [1,2,3]
iterates each and recursive down unchoosed:
1->(2,3): 2->(3), 3->(2);
2->(1,3): 1->(3), 3->(1);
3->(1,2): 1->(2), 2->(1);

How to arrive:
* Problem is how to pass down already choosed nums, and look for not choosed nums.
* Array is not good for this situation, because choose the mid nums first, then pass down subarray of not choosed.
* Ex: [1,2,3,4]; choosed [2,3] -> passdown (1,4); 
* To make that works, we can use ArrayList instead of array. 
* That way choose num means list.add(num); And we also can remove(num) to choose another elm for cur pos.
* Algorithm:
* Define a member Res nested list to add all possible permutations.
* Define a helper function with list, and nums.
* In helper function:
	* **Base case**:
	* If list.size == nums.len:
		* we found a new permutation.
		* res.add(new List(list)); 
		* define new list, or the referrenced list will be changed in future recursive calls.
	* **Recursive Case**:
	* each recursive call represents: **choose a unchoosed num for cur pos**;
	* we need to Try out all unchoosed, and pass down that num as choosed for cur pos.
	* Loop nums:
		* if list not contains nums[i]: (Found unchoosed num)
			* list.add(nums[i]) (choose this num for cur pos);
			* pass down recursively to helper(list, nums); Go find unchoosed for next pos.
			* list.remove(list.size - 1); Remove just choosed, so we can try another num for cur pos.
* In the main function return the Res nested list.
* Notice: list.contains(num) is O(n) could be optimized to hashSet.
* Time: O(n!); choose one (n-1) for next, do that for n time;
* Space: O(n!); All permutations nums, which is n!(n-r)!; n=r in this case, so just n!;
* Auxillary Space: O(n) for recursive call n times;
*/

class Permutations {

	/**
	 * Solution: backtracking, recursive and loop unchoosed nums.
	 * Time: O(n!); list.contains(num) is O(n) could be optimized to hashSet.
	 * Space: O(n!); All permutations nums, which is n!(n-r)!; n=r in this case, so just n!;
	 * Auxillary Space: O(n) for recursive call n times;
	 */
	private List<List<Integer>> res = new ArrayList<>();

  public List<List<Integer>> permute(int[] nums) {
    helper(new ArrayList<>(), nums);
    return res;
  }
  
  public void helper(List<Integer> list, int[] nums) {
    if (list.size() == nums.length) {
      // add new permutation.
      // use a new list, so the referrenced list won't be changed in future.
      res.add(new ArrayList<Integer>(list));
      return;
    }
    // loop for unchoosed nums
    for (int i = 0; i < nums.length; i++) {
      // add not choosed num as cur pick.
      if (!list.contains(nums[i])) {
        list.add(nums[i]);
        helper(list, nums);
        // remove cur num, for the next iteration.
        // Ex: 1 -> (2,3); then 2 -> (1,3); then 3->(1,2);
        list.remove(list.size() - 1);
      }
    }
  }
  
}