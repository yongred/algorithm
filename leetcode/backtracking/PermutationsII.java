

/**
Solution: backtracking, Sort, recursive and loop unchoosed nums with Hash of indexes.
Ex: 1,2,1 => sort 1,1,2
1->(1,2): 1->(2), 2->(1); [1,1,2] [1,2,1]
1 same as index0, and index0 is unchoosed too, Skip.
2->(1,1): 1->(1), skip same 1; [2,1,1]
ANS: [1,1,2] [1,2,1] [2,1,1];

How to arrive:
* Same as Permutation I, Each recursive call is for try choosing unchoosed nums for cur pos.
* One difference is that since there is duplicates, we cannot use list.contains(num); b/c there will be more than 1, 
* Instead we can use **A hash for indexes**, index is unique. Check if the index of elm is choosed.
* Different thing is we need to skip the duplicates. 
* Ex: 1,2,1 => we get 2 [1,1,2] from starting index0 and index2.
* To Skip duplicates, we can sort the array first, that way if the prev is the same will can skip cur.
* **Key**: When checking if cur is duplicate; Compare to prev num, but we only compare if prevNum is unchosen like cur, **We don't care about choosed, only if unchosen ones are duplicates**;
```
if (i > 0 && nums[i - 1] == nums[i] && !choosedIndexes.contains(i - 1)) {
	continue;
}
```
* Time: O(n!)
* Space: O(n!) for result nested list for all permutations. Auxillary Space: is O(n) for recursion.
*/
class PermutationsII {

	/**
	 * Solution: backtracking, Sort, recursive and loop unchoosed nums with Hash of indexes.
Ex: 1,2,1 => sort 1,1,2
	 * Time: O(n!)
	 * Space: O(n!) for result nested list for all permutations. Auxillary Space: is O(n) for recursion.
	 */
  private List<List<Integer>> res = new ArrayList<>();
  
  public List<List<Integer>> permuteUnique(int[] nums) {
    // sort so we can skip next.
    Arrays.sort(nums);
    // since there is duplicates, we use indexes (hash) to check which one is choosed.
    helper(new ArrayList<>(), new HashSet<>(), nums);
    return res;
  }
  
  public void helper(List<Integer> perm, HashSet<Integer> choosedIndexes, int[] nums) {
    if (perm.size() == nums.length) {
      res.add(new ArrayList<Integer>(perm));
      return;
    }
    // loop nums, try all unchoosed nums for cur pos.
    for (int i = 0; i < nums.length; i++) {
      // skip duplicated if prev dupl is not choosed also.
      if (i > 0 && nums[i - 1] == nums[i] && !choosedIndexes.contains(i - 1)) {
        continue;
      }
      // found unchoosed num.
      if (!choosedIndexes.contains(i)) {
        // try choose cur unchoosed num.
        perm.add(nums[i]);
        // hash used for checking which index num is choosed.
        choosedIndexes.add(i);
        // go check next pos
        helper(perm, choosedIndexes, nums);
        // remove cur choosed, so we can try choose another num for cur pos.
        perm.remove(perm.size() - 1);
        choosedIndexes.remove(i);
      }
    }
  }
  
}