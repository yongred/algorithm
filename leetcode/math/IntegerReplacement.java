/**
397. Integer Replacement
Given a positive integer n and you can do operations as follow:

If n is even, replace n with n/2.
If n is odd, you can replace n with either n + 1 or n - 1.
What is the minimum number of replacements needed for n to become 1?

Example 1:

Input:
8

Output:
3

Explanation:
8 -> 4 -> 2 -> 1
Example 2:

Input:
7

Output:
4

Explanation:
7 -> 8 -> 4 -> 2 -> 1
or
7 -> 6 -> 3 -> 2 -> 1
*/

/*
Solution 1: Memotization. Top-down, prevented memories limit exceed, and stack, integer overflow.
memo dp.
n=9:
res = 1 + Min(f(9-1), f(9+1))
f(9-1):n=8
8 = 1+ f(8/2)-> 4 = 1+ f(4/2) -> 2 = 1+ f(2/2) -> 1 ret 0;
8 = 1+1+1+0 = 3;
f(9+1):n=10
10 = 1+ f(10/2)-> 5 = Min(f(5-1), f(5+1)) ...

How to arrive:
* The question already give us all the recursive cases and base case.
* Base case:
	* n == 1: return 0;
* Recursive case:
	* Even: num -> n/2; So f(n/2);
	* Odd: num is the min between, f(n+1) or f(n-1);
* Now we have to be extra careful of the corner cases:
* Corner case: **Memorize data structure Memory limit Exceeded**:
	* use **hashmap** for storing traversed num, because int[] requires n memories.
   * hashmap only lg(n). if n=10000... **limit exceed using int[]**.
* Corner case: **Integer & stack overflow**:
	* Int MAX = 2147483647;
	* when calc Odd: f(n+1) will overflow; So, we need to change it to something equal
	* f(n+1) = f((n+1)/2)+1 = f(1+ (n-1)/2) + 1;
	* mid = start + (end - start) / 2; 1+ (n-1)/2 = (n+1)/2;
* time: O(lgn)
* Space: O(lgn)

*/

class IntegerReplacement {

	/**
	 * Solution 1: memotization. Top-down, prevented memories limit exceed, and stack, integer overflow.
	 * time: O(lgn)
	 * Space: O(lgn)
	 */
  public int integerReplacement(int n) {
    // use hashmap for storing traversed num, because int[] requires n memories.
    // hashmap only lgn. if n=10000... limit exceed using int[].
    return helper(n, new HashMap<Integer, Integer>());
  }
  
  public int helper(int n, HashMap<Integer, Integer> memo) {
    // base case n =1
    if (n == 1) {
      return 0;
    }
    // memorized.
    if (memo.containsKey(n)) {
      return memo.get(n);
    }
    int res = 0;
    // even
    if (n % 2 == 0) {
       res = 1 + helper(n / 2, memo);
    } else {
      // odd, f(n+1), f(n-1).
      // prevent integer & stack overflow (n + 1); use f(1 + (n - 1) / 2) + 1;
      // f(n+1) = f((n+1)/2)+1 = f(1+ (n-1)/2) + 1;
      // mid = start + (end - start)/2; 1+(n-1)/2 = (n+1)/2;
      res = 1 + Math.min(helper(n - 1, memo), helper(1 + (n - 1) / 2, memo) + 1);
    }
    // memorize cur n ans.
    memo.put(n, res);
    return res;
  }
}