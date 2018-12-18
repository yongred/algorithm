/**
39. Combination Sum
Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.

The same repeated number may be chosen from candidates unlimited number of times.

Note:

All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: candidates = [2,3,6,7], target = 7,
A solution set is:
[
  [7],
  [2,2,3]
]
Example 2:

Input: candidates = [2,3,5], target = 8,
A solution set is:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]
*/

class CombinationSum {
  
  /**
   * Solution: Backtracking
   * Time: O(candidates^target); loop candidates every recurse call until target = 0;
   * Space: O(candidates^target)
   */
  private List<List<Integer>> res = new ArrayList<>();
  
  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    helper(candidates, target, new ArrayList<>(), 0);
    return res;
  }
  
  public void helper(int[] candidates, int target, List<Integer> combo, int start) {
    if (target < 0) {
      return;
    }
    if (target == 0) {
      res.add(new ArrayList<Integer>(combo));
      return;
    }
    for (int i = start; i < candidates.length; i++) {
      combo.add(candidates[i]);
      // we can reuse i elm, Ex: [2,2,3] = target 7 in {2,3,6,7}
      helper(candidates, target - candidates[i], combo, i);
      combo.remove(combo.size() - 1);
    }
  }
}