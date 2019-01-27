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

/**
Solution: Backtracking
Ex: [2,3,6,7] tar=7;
Res: [2,2,3], [7];
2 -> (2,3,6,7): [2,2] -> (2,3,6,7): [2,2,2] -> (2,3,6,7) -> [2,2,2,2] 8 > 7 return;
[2,2,2] -> 3,6,7 sum > 7 return; [2,2,3]->(2,3,6,7): 7=7 Add to res;
3 -> (3,6,7): [3,3] -> (6,7): [3,3] 6,7 sum > 7; return;
6->(6,7) sum > 7; return;
7 -> (7): 7==7, Add to res;

How to Arrive:
* To get all combos, we choose a num in curPos, then go choose for next pos with other avaliable candidates until sum = target; 
* If candidate choosed for curPos not leads to target, then we remove this candidate and choose other candidates for cur position.
* The question says it wants all Unique combos so order doesn't matter, Ex: [2,2,3] = [2,3,2];
* B/c Order doesn't matter, we don't want to go back to prev num and choose again for diff positions, like if we have [2,3] when 3 is at first pos [3] we don't want [3,2];
* Therefore we need a Param startIdx to indicates nums before startIdx are all tested/combined with all other nums, no need to choose them again.
* Because we can choose cur candidate repeatly, we include cur startIdx as begin point, That means it is still an avaliable Candidate to choose.
* Time: O(candidates^target); loop candidates every recurse call until target = 0;
* Space: O(candidates^target)
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