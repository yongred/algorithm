/**
338. Counting Bits
Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.

Example 1:

Input: 2
Output: [0,1,1]
Example 2:

Input: 5
Output: [0,1,1,2,1,2]
Follow up:

It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n) /possibly in a single pass?
Space complexity should be O(n).
Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
*/

/**
Solution: DP, find the pattern
Ex:
0: 0000 = 0;  [0]=0
1: 0001 = 1;  [1]= [0]+1; 2^0
2: 0010 = 1;  [2]= [0]+1; 2^1
3: 0011 = 2;  [3]= [1]+1
4: 0100 = 1;  [4]= [0]+1; 2^2
5: 0101 = 2;  [5]= [1]+1
6: 0110 = 2;  [6]= [2]+1
7: 0111 = 3;  [7]= [3]+1
8: 1000 = 1;  [8]= [0]+1; 2^4
9: 1001 = 2;  [9]= [1]+1
10: 1010 = 2;  [10]= [2]+1
11: 1011 = 3;  [11]= [3]+1
12: 1100 = 2;  [12]= [4]+1
13: 1101 = 3;  [13]= [5]+1
14: 1110 = 3;  [14]= [6]+1
15: 1111 = 4;  [15]= [7]+1

0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
0 1 1 2 1 2 2 3 1 2 2  3  2  3  3  4

How to arrive:
* Question wants us to solve in O(n) time; Which means we cannot count bits num by num.
* We can try to find a relationship between prev count and cur count. (A Pattern);
* The result output looks like a DP array.
* So we list out the first 16 nums count.
* We found [0] = 0, Like base case; [1] = 1 can be [0]+1; [2] =1 can also be [0]+1;
	* [3] = 2, can be [1]+1; [4]=1, again [0]+1; [5]=2, [1]+1; [6]=2, can be [2]+1;
* We found that for every 2's exponent val: 2^0, 2^1, 2^2.. There is something repeating. 2^x = [0] + 1; Then nums after that is [1]+1, [2]+1, [3]+1... until 2^(x+1) -1;
* The rule looks like: dp[i] = dp[i - 2^i] + 1;
* Time: O(n)
* space: O(n)

*/

class CountingBits {

	/**
	 * Solution: DP, find a pattern.
	 * Time: O(n)
	 * space: O(n)
	 */
	public int[] countBits(int num) {
    // dp[i] = dp[i - 2^i] + 1;
    int[] dp = new int[num + 1];
    int curTwosVal = 1; // 2^0 = 1;
    for (int i = 1; i <= num; i++) {
      // check if i is at next 2's power.
      if (curTwosVal * 2 == i) {
        curTwosVal *= 2;
      }
      dp[i] = dp[i - curTwosVal] + 1;
    }
    return dp;
  }

	/**
	 * Solution: Naive, counting every bits of every number.
	 * Time: O(n * bits);
	 * Space: O(n)
	 */
  public int[] countBits(int num) {
    // count 1 by 1.
    int[] res = new int[num + 1];
    for (int i = 0; i <= num; i++) {
      int count = 0;
      // don't use i, still need it as index. Cause infinite loop. i would be 0 forever.
      int cur = i;
      // use >> right shift, & 1 to check is 1.
      // ex: 101 & 001 = 1 cnt=1, 101 >> 1 = 10, 10 & 01 = 0;
      // 10 >> 1 = 1, 1 & 1 = 1 cnt=2; 1 >> 1 = 0; done.
      while (cur != 0) {
        // ( & ) parentheses, & smaller precedence than ==;
        if ((cur & 1) == 1) {
          count++;
        }
        cur >>= 1;
      }
      res[i] = count;
    }
    return res;
  }
  
}