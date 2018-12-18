

/**
[1,2,3]
ANS: [],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3];
-> recursive; | iter
[]; 
1 [1] -> (2,3): 2 [1,2] -> (3): 3 [1,2,3];
								rm (2)
                3  [1,3]; 
rm (1)                
2 [2] -> (3): 3 [2,3];
rm (2)
3 [3]
*/
class Subsets {

  private List<List<Integer>> res = new ArrayList<>();
  
  public List<List<Integer>> subsets(int[] nums) {
    // choose 1st pos from all nums.
    helper(nums, new ArrayList<>(), 0);
    return res;
  }
  
  public void helper(int[] nums, List<Integer> subsets, int start) {
  	// add currently filled positions of nums, like [], or [1,2]
    // use a new list copy, reference will override in future passes.
    res.add(new ArrayList<Integer>(subsets));
    // try all unchoosed nums for curPos. start->end
    for (int i = start; i < nums.length; i++) {
      // choose curNum
      subsets.add(nums[i]);
      // now choose nextPos with rest of the unchoosed With the CurNum.
      helper(nums, subsets, i + 1);
      // remove curNum, and try other unchoosed nums for curPos.
      subsets.remove(subsets.size() - 1);
    }
  }
}