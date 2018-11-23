
/**
Solution: Recursive
How to Arrive:
* We are looking for least # Perfect Square combo. It means we can add cur PS which is counted as 1 to func(n-ps);
* Until Base case of n == 0, which is 0; or n==1, which is 1;
* We count # starting with 1, then 4, then 9 ... x^2 <= n; Then do the same with 2sec, then 3rd # in the combo. If count is smaller than least, we update.
* Ex: 9 = {1,1,..} or {1,4,4} {4,1,4} or {4,4,1} or {9}; Start with 1 or 4, or 9. then combine 2nd 1 or 4 or ..;
* We loop from 1 to i^2 <= n; Count = 1 + func(n - i^2);
* Time: O((n^1/2)^n); Loop n^1/2 and recurse down till n=0;
* Space: O(n);

Solution: DP 1d array
How to Arrive:
* There are alot of repeats, Ex: 9 => 1,1,1,1 func(5); 9 => 4, func(5); 
* That means we can save the prev calc least count, and use it for future calc.
* We can have a 1d array DP that saves 1->n least counts.
* Base: n=0 is 0; n=1 is 1;
* We need to init dp from 1->n; Means a loop from 1 to n;
* Now each iter, we need to find that num's least PS combo.
* To do that we need to search each PS <= curNum; And compare to cur least.
* Ex: 18 => {1,1,4,4,4,4} or {4,4,1,9} or {9,9}; But we have prev Saved now. So {1, dp[17]}, or {4, dp[14]}, or {9, dp[9]};
* Notice the Max # of PS is always n itself, b/c 1 is a PS, so you can have all 1s. We can use that as init val, then loop through all PS <= curNum, update the least count.
* Find until we have dp[n];
* Time: O(n^1(1/2));
* Space: O(n);
*/

class PerfectSquares {

	public int numSquares(int n) {
    // store every # <= n, least # of PS
    int[] dp = new int[n + 1];
    // fill in least # of PS for every num <= n;
    for (int i = 1; i <= n; i++) {
      // max of n perfect squares is n; b/c 1^2=1, n # of 1s
      dp[i] = i;
      // now check every PS <= curNum, see which one led to least #.
      for (int j = 1; j * j <= i; j++) {
        int sq = j * j;
        // cur sq counts as 1 + min # PS at i-sq pos;
        // Ex: n=6, sq=4 6-4=2, dp[2]+dp[4] is {1,1,4} = 3;
        dp[i] = Math.min(dp[i], dp[i - sq] + 1);
      }
    }
    return dp[n];
  }

	/**
	 * Solution: recursive brute force
	 */
  public int numSquaresRecursive(int n) {
    // 0 is 0, 1 is 1.. 3 is 3 1s
    if (n <= 3) {
      return n;
    }
    // most is 1+1+1...= n;
    int least = n;
    int count = 0;
    for (int i = 1; i * i <= n; i++) {
      int sq = i * i;
      count = numSquaresRecursive(n - sq) + 1;
      if (count < least) {
        least = count;
      }
    }
    return least;
  }
  
}