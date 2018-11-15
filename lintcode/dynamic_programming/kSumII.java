/**
90. k Sum II
Given n unique integers, number k (1<=k<=n) and target.

Find all possible k integers where their sum is target.

Example
Given [1,2,3,4], k = 2, target = 5. Return:

[
  [1,4],
  [2,3]
]
*/

/**
Solution: Depth First Search, better Space.
How to Arrive:
* Same as the k Sum recursive solution.
* There are 2 steps in curNum. We check if curNum leads an ans. Then check next Num leads to an ans.
	* So we either **goes down** to check (k-1, target-curNum). Until it reach *k=0 or tar = 0 (base case);* Add list to res if found.
	* Then we go parallel to siblings if it leads to an Ans. (k, target) since not include curNum.
	* **Same as DFS**: check for deepest routes first, then goes to parallel gates and search.
* We are Returning Lists. So pass down list to add elms.
* When we want to include curNum, list.add(curNum);
* **Key**: remember to remove curNum, list.remove(list.size()-1); After deep traverse, Before next sibling in parallel.
* Time: O(n^k);
* Space: O(k);

Solution: Dynamic Programming, Then trace.  better Time.
How to Arrive:
* Think about K Sum DP solution. We will create the same table. dp[k+1][n+1][t+1];
* Then we Trace back from last position. dp[k][n][target];
* When we get the count of target we can trace back <- and \ dir to get the elms add up to target.
   * There are 2 posibilities, 
   	 * whether the count is carry over from left of this row. Meaning curVal not included.
   	 * or curVal is included, and the count is add from \ dir.
   	 * You can have some from \ and some from <-.
   	 * To know if curVal have contribution, compare it the <- count. Ex: curCount = 3; <- count = 1; Means we have 1 from left, 2 from \ dir.
   	 * When go to left, we want to pass same target, b/c curVal is not contributed.
   	 * When go to \ dir, we want to pass (target - curVal), since curVal contributed.
   * Base case: when target = 0; we know all k elms added to list. Also k=0 means we are at end.
   * Warning: pass list as copy, or else the list will carry on with the recursion. Having all the elms from prev call.
 * Time: O(nkt);
 * Space: O(nkt);
*/

import java.util.*;
import java.io.*;

public class KSumII {

	private List<List<Integer>> res = new ArrayList<>();
    
  /*
   * @param A: an integer array
   * @param k: a postive integer <= length(A)
   * @param target: an integer
   * @return: A list of lists of integer
   * Solution: DFS
   * Time: O((n-k)^k);
   * Space: O(k)
   */
  public List<List<Integer>> kSumIIDfs(int[] A, int k, int target) {
    dfs(A, k, target, new ArrayList<>(), 0);
    return this.res;
  }
  
  public void dfs(int[] A, int k, int target, List<Integer> list, int index) {
    if (k <= 0) {
      if (target == 0) {
      	// must create new list copy. Otherwise reference is same. End up having identical lists.
        this.res.add(new ArrayList<Integer>(list));
      }
      return;
    }
    
    for (int i = index; i <= A.length - k; i++) {
      if (A[i] > target) {
        continue;
      }
      list.add(A[i]);
      dfs(A, k - 1, target - A[i], list, i + 1);
      // think from bottom up. All elms added in the bottom calls are removed.
      // Ex: [2,4,3,1,5] k3 +2 -> k2 +4 -> k1 +3 -> k0 return -3, -4, -2.
      list.remove(list.size() - 1);
    }
    
  }


  /*
   * @param A: an integer array
   * @param k: a postive integer <= length(A)
   * @param target: an integer
   * @return: A list of lists of integer
   */
  public List<List<Integer>> kSumII(int[] A, int k, int target) {
    // table for choose k elms from n choices, how many combination == target.
    // Records all posibility for choosing 1->k elms in A[1]->A[n-1] elms, 
    // Count any values <= target 
    int [][][] dp = new int[k + 1][A.length + 1][target + 1];
    
    // Fill the k=1 posibilities first. Use it for further k=2,3..k.
    for (int nInd = 1; nInd <= A.length; nInd++) {
      // this row is only the posibilities for cur value and the prev values.
      for (int tInd = nInd; tInd > 0; tInd--) {
        // any num in arr <= target, count appearance 1.
        if (A[tInd - 1] <= target) {
          dp[1][nInd][A[tInd - 1]] = 1;
        }
      }
    }
    
    // Fill the whole table starting k=2;
    for (int kInd = 2; kInd <= k; kInd++) {
      // 2 nums start from 2nd val, 3 nums start in 3rd val.
      // including the prev vals.
      for (int n = kInd; n <= A.length; n++) {
        // check all combinations add up to <= target. 
        for (int t = 0; t <= target; t++) {
          // prev row cnts add to cur cnts. 
          dp[kInd][n][t] += dp[kInd][n - 1][t];
          // check \ row. What is on k-1 n-1 vals.
          // t + A[n-1] means check for a prevNum + curnum <= target.
          if (t + A[n - 1] <= target) {
            dp[kInd][n][t + A[n - 1]] += dp[kInd - 1][n - 1][t];
          }
        }
      }
    }

    List<List<Integer>> res = new ArrayList<>();
    addToList(dp, A, k, A.length, target, new ArrayList<Integer>(), res);
    return res;
  }
  
  public void addToList(int [][][] dp, int[] A, int k, int n, int t, 
    		List<Integer> list, List<List<Integer>> res) {
  	if (n < 0) {
  		return;
  	}

  	if (k == 0 || t == 0) {
  		res.add(list);
  		return;
  	}

  	// go to left, passing new list, so it does not alter the values
  	if (dp[k][n - 1][t] > 0) {
  		addToList(dp, A, k, n - 1, t, new ArrayList<Integer>(list), res);
  	}

  	// go \ dir.
  	if (dp[k][n][t] > dp[k][n - 1][t]) {
  		list.add(A[n - 1]);
  		addToList(dp, A, k - 1, n - 1, t - A[n - 1], new ArrayList<Integer>(list), res);
  	}
  	
  }
}