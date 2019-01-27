/**
40. Combination Sum II

Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.

Each number in candidates may only be used once in the combination.

Note:

All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: candidates = [10,1,2,7,6,1,5], target = 8,
A solution set is:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
Example 2:

Input: candidates = [2,5,2,1,2], target = 5,
A solution set is:
[
  [1,2,2],
  [5]
]
*/

/**
Solution: Backtracking, Sort and skip prev == num to prevent duplicates;
Ex: [2,3,2,6,1,1] tar=6;
Sort: [1,1,2,2,3,6]
Res: [1,2,3] [1,1,2,2] [6];
[1]-> (1,2,2,3,6): [1,2]->(2,3,6): [1,2,2]->(3,6) sum > 6 return;
[1,2,3] 6=6 add to res; [1,2,6] sum > 6 return;
[1,1]->(2,2,3,6): [1,1,2]->(2,3,6): [1,1,2,2] == 6 Add to res;
[1,3]->(6) > 6 return;
[1] Skip idx1, 1 == prev1 (idx0); Skip.
[2]->(2,3,6): [2,2]->(3,6) sum > 6 return;
[2] Skip idx3 (2) == idx2(2) skip.
[3]->(6) sum > 6 return;
[6] == 6 add to res;

How to Arrive:
* To get all combos, we choose a num in curPos, then go choose for next pos with other avaliable candidates until sum = target; 
* If candidate choosed for curPos not leads to target, then we remove this candidate and choose other candidates for cur position.
* The question says it wants all Unique combos so order doesn't matter, Ex: [2,2,3] = [2,3,2];
* B/c Order doesn't matter, we don't want to go back to prev num and choose again for diff positions, like if we have [2,3] when 3 is at first pos [3] we don't want [3,2];
* Therefore we need a Param startIdx to indicates nums before startIdx are all tested/combined with all other nums, no need to choose them again.
* We can only choose the cur candidates Once, so when recursive call to next position, we start with curIndex+1, b/c this num only able to use once.
* B/c there are Duplicates nums, to prevent duplicate Combos, we must not choose the same value candidate for Cur Position more than once.
* To prevent that we **Sort** the Nums, that way we can compare curNum == prevNum; if ==, skip for this Position.
* Time: O(candidates^target); loop candidates every recurse call until target = 0;
* Space: O(candidates^target)
*/

class CombinationSumII {

	/**
	 * Solution: Backtracking, Sort to check duplicates.
	 * Time: O(n^t); 
	 * Space: O(n^t)
	 */
  List<List<Integer>> res = new ArrayList<>();
  
  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    helper(candidates, target, new ArrayList<>(), 0);
    return res;
  }
  
  public void helper(int[] candidates, int target, List<Integer> list, int start) {
    if (target < 0) {
      return;
    }
    if (target == 0) {
      res.add(new ArrayList<>(list));
      return;
    }
    
    for (int i = start; i < candidates.length; i++) {
      // check duplicates, prev is = cur.
      if (i > start && candidates[i] == candidates[i - 1]) {
        continue;
      }
      list.add(candidates[i]);
      helper(candidates, target - candidates[i], list, i + 1);
      list.remove(list.size() - 1);
    }
  }
  
  
}