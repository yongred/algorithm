/**
89. k Sum
Given n distinct positive integers, integer k (k <= n) and a number target.

Find k numbers where sum is target. Calculate how many solutions there are?

Example
Given [1,2,3,4], k = 2, target = 5.

There are 2 solutions: [1,4] and [2,3].

Return 2.
*/

/**
Solution: recursive
How to arrive:
* think of solving it as smaller problem. In this case, k -> k-1 -> k-2;
* We want the count of the solutions. Ex: k=3; count(k) += count(k-1);
* We are finding sum of k nums == target. 
	* So, if we want to check cur Int leads to result, we just pass count(k-1, target - curInt, subarr[len-1]);
	* We also, want to consider posibility that not include curInt, so pass count(k, target, subarr[len-1]);
		* b/c *we not including curInt, so k and target is still the same*. We just look for k nums in subarr.
	* subarr[len-1] is subarr not including curInt.
	* Now the total is **count(k-1, target - curInt, subarr[len-1]) + count(k, target, subarr[len-1])**
	* You can also use a ForLoop for not including curInt, it's the same thing. Both just consider the next num as starting, and find k and target.
* What is the Base case?
	* We know that when k=0; it's 0; 
	* When k=1, we only need to search linear, so if we find that left amount target, we can return 1 (all # uniq); Not found return 0;
* Time: O(n^k); recurse until k=1; We have n-k combinations to search for.
* Space: O(k);

Solution: Dynamic Programming 3D table:
int [] arr = {2,4,3,1,5};
int k = 3;
int tar = 8;
[

k=0
[
t  = 	 0  1  2  3  4  5  6  7  8
n=0 [0, 0, 0, 0, 0, 0, 0, 0, 0], n=2 [0, 0, 0, 0, 0, 0, 0, 0, 0], n=4 [0, 0, 0, 0, 0, 0, 0, 0, 0], n=3 [0, 0, 0, 0, 0, 0, 0, 0, 0], n=1 [0, 0, 0, 0, 0, 0, 0, 0, 0], n=5 [0, 0, 0, 0, 0, 0, 0, 0, 0]
], 

k=1
[
t  = 	 0  1  2  3  4  5  6  7  8
n=0 [0, 0, 0, 0, 0, 0, 0, 0, 0],  n=2 [0, 0, 1, 0, 0, 0, 0, 0, 0],  n=4 [0, 0, 1, 0, 1, 0, 0, 0, 0], n=3 [0, 0, 1, 1, 1, 0, 0, 0, 0], n=1 [0, 1, 1, 1, 1, 0, 0, 0, 0], n=5 [0, 1, 1, 1, 1, 1, 0, 0, 0]
], 

k=2
[
t  = 	 0  1  2  3  4  5  6  7  8
n=0 [0, 0, 0, 0, 0, 0, 0, 0, 0], n=2 [0, 0, 0, 0, 0, 0, 0, 0, 0],  n=4 [0, 0, 0, 0, 0, 0, 1, 0, 0], n=3 [0, 0, 0, 0, 0, 1, 1, 1, 0], n=1 [0, 0, 0, 1, 1, 2, 1, 1, 0], n=5 [0, 0, 0, 1, 1, 2, 2, 2, 1]
], 

k=3
[
t=	0  1  2  3  4  5  6  7  8
n=0[0, 0, 0, 0, 0, 0, 0, 0, 0], n=2[0, 0, 0, 0, 0, 0, 0, 0, 0], n=4[0, 0, 0, 0, 0, 0, 0, 0, 0], n=3[0, 0, 0, 0, 0, 0, 0, 0, 0], n=1[0, 0, 0, 0, 0, 0, 1, 1, 1], n=5[0, 0, 0, 0, 0, 0, 1, 1, 2]
]

]

How to Arrive:
* Dynamic Programming: use table to record all smaller problem posibilities, and use it to find the target.
* What we finding? The total # ways that sum(k nums) == target.
* Different from recursion. Instead of top-down, k(3) += k(2) += k(1); *We do bottom-up. We find the k=1 first, all posibilities, then use it for later k=2, k=3*.
* To do that we need a table that considers  3 vals: k, n (A.len), t (target).
	* **How this work?** Consider the table above. There are K nums to find, to add up to target. There are n vals to consider. And all vals <= target can add up to target. dp[k+1][n+1][t+1]; +1 for the 0 positions.
	*  Ex: dp[3][5][8] = 2; meaning: Sum of (3 positions choosing from 5 nums) that == 8, Have 2 posible combinations.
	*  **How to use prev records?** 
		*  We first init the k=1 row, when reaching to cur n val, how many vals <= target. **include cur n val and prev ns**. 
		*  Ex: when reaching to k=1, nInd=2, nVal=4;  We have a 2 & 4. So we count 1 on both vals.
		*  Now when we reach to k=2, nInd=3, nVal=3; We are now finding Including curVal 3, which mix of 2 nums have <= target.
		*  So we consider k=1, nInd=2 row. It has the posible vals for 1 num upto nInd=2.
		*  Ex: curVal =3, we consider combos with 2 & 4; results 3+2=5 <=target, 3+4=7 <= target. So record 5,7 as count 1.
		*  Now don't forget to carry over the prev vals from the k=2 row. So 6 have count 1 also.
	* Now when we reach to the last elm of k row. It is the total count we are looking for. It is the Target val Count for k nums from n choices.
* Time: O(nkt);
* Space: O(nkt);
*/

import java.util.*;
import java.io.*;

public class KSum {

	/**
	 * @param A: An integer array
	 * @param k: A positive integer (k <= length(A))
	 * @param target: An integer
	 * @return: An integer
	 */
	public static int kSum(int[] A, int k, int target) {
        if (A == null || A.length < k || k <= 0) {
          return 0;
        }
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

        return dp[k][A.length][target];
    }

  /**
   *
   */
  public static void kSumPrint(int[] A, int k, int target) {
  	if (A == null || A.length < k || k <= 0) {
      return;
    }
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

    for (int i = 0; i < res.size(); i++) {
			for (int num : res.get(i)) {
				System.out.print(num + ", ");
			}
			System.out.println();
		}
  }

  /**
   * t is target val
   * Solution:
   * When we get the count of target we can trace back <- and \ dir to get the elms add up to target.
   * There are 2 posibilities, 
   	 * whether the count is carry over from left of this row. Meaning curVal not included.
   	 * or curVal is included, and the count is add from \ dir.
   	 * You can have some from \ and some from <-.
   	 * To know if curVal have contribution, compare it the <- count. if curCount = 3; <- count = 1; Means we have 1 from left, 2 from \ dir.
   	 * When go to left, we want to pass same target, b/c curVal is not contributed.
   	 * When go to \ dir, we want to pass (target - curVal), since curVal contributed.
   * Base case: when target = 0; we know all k elms added to list. Also k=0 means we are at end.
   * Warning: pass list as copy, or else the list will carry on with the recursion. Having all the elms from prev call.
   * 
   * Time: O(k)
   * Space: O(k)
   */
  public static void addToList(int [][][] dp, int[] A, int k, int n, int t, 
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

	/**
   * @param A: An integer array
   * @param k: A positive integer (k <= length(A))
   * @param target: An integer
   * @return: An integer
   * Time: O(n^k) = T((n-k)^k); goes down until k.
   * Space: O(n)
   */
  public int kSumRecursive(int[] A, int k, int target) {
    if (A == null || k <= 0 || A.length < k) {
      return 0;
    }
    int count = 0;
    
    if (k == 1) {
      for (int i = 0; i < A.length; i++) {
        if (A[i] == target) {
          return 1;
        }
      }
      return 0;
    }
    
    for (int i = 0; i <= A.length - k; i++) {
      if (A[i] > target) {
        continue;
      }
      int [] subarr = Arrays.copyOfRange(A, i + 1, A.length);
      count += kSumRecursive(subarr, k - 1, target - A[i]);
    }
    
    return count;
  }

  /**
   * @param A: An integer array
   * @param k: A positive integer (k <= length(A))
   * @param target: An integer
   * @return: An integer
   * Time: O(n^k)
   * Space: O(n)
   */
  public int kSumRecursive2(int[] A, int k, int target) {
    if (A == null || k <= 0 || A.length < k) {
      return 0;
    }
    int count = 0;
    
    if (k == 1) {
      for (int i = 0; i < A.length; i++) {
        if (A[i] == target) {
          return 1;
        }
      }
      return 0;
    }
    
    int [] subarr = new int[A.length - 1];
    // check this when k > 1; but only 1 elm is left.
    if (A.length > 1) {
      subarr = Arrays.copyOfRange(A, 1, A.length);
    }
    // either count cur elm or don't count cur elm.
    count = kSumRecursive2(subarr, k - 1, target - A[0]) + kSumRecursive2(subarr, k, target);
    
    return count;
  }

	public static void main(String[] args) {
		int [] arr = {2,4,3,1,5,6,9,7,8};
		int k = 2;
		int tar = 10;
		int ans = kSum(arr, k, tar);
		System.out.println(ans);
		kSumPrint(arr, k, tar);
		
	}
}