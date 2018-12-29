/**
89. Gray Code
The gray code is a binary numeral system where two successive values differ in only one bit.

Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.

Example 1:

Input: 2
Output: [0,1,3,2]
Explanation:
00 - 0
01 - 1
11 - 3
10 - 2

For a given n, a gray code sequence may not be uniquely defined.
For example, [0,2,3,1] is also a valid gray code sequence.

00 - 0
10 - 2
11 - 3
01 - 1
Example 2:

Input: 0
Output: [0]
Explanation: We define the gray code sequence to begin with 0.
             A gray code sequence of n has size = 2n, which for n = 0 the size is 20 = 1.
             Therefore, for n = 0 the gray code sequence is [0].
*/

/**
Solution 1: using formula G(i) = i ^ i>>1
How to arrive:
* If we know this formula, then it is straight forward.
* Since Gray Code is 2^n size; 
* We loop 0 -> 2^n -1:
	* Add i ^ i >> 1 to reslist.
* Time: O(2^n); gray code have 2^n numbers. Linear runtime since we only traverse once.
* Space: O(2^n);

Solution 2: Backtrack loop.
Ex:
n=0  -> 0
n=1  -> 0, 1
n=2  -> (00,  01),  (11,  10)
n=3  -> (000, 001, 011, 010), (110, 111, 101, 100)
1) prepend 0 to prev nums, 
2) reverse the order, prepend 1 to prev nums.
prepend 0 doesn't change the vals, no need to do anything.
prepend 1 will change the num, shift 1 << ith pos. mask | prevNums;

How to Arrive:
* There is a backtracking pattern here.
* n Gray Code = prepend 0 to n-1 Gray Code, Then in reverse order prepend 1 to all n-1 Gray Code. Like the example above.
* 
* time: O(n * 2^n); gray code have 2^n numbers. Count as O(n^2) since that is the general amount;
* Space: O(2^n);
	
*/

class GrayCode {

	/**
	 * Solution 1: using formula G(i) = i ^ i>>1
	 * Time: O(2^n); gray code have 2^n numbers. Linear runtime since we only traverse once.
	 * Space: O(2^n);
	 */
  public List<Integer> grayCode(int n) {
    // formula G(i) = i ^ i>>1;
    List<Integer> res = new ArrayList<>();
    // GrayCode seq size = 2^n; pow(2, n) or (1 << n) = 1 * 2 n times;
    for (int i = 0; i < (1 << n); i++) {
      res.add(i ^ i >> 1);
    }
    return res;
  }

  /**
   * Solution 2: Backtrack loop.
   * time: O(n * 2^n); gray code have 2^n numbers. Count as O(n^2) since that is the general amount;
   * Space: O(2^n)
			n=0  -> 0
			n=1  -> 0, 1
			n=2  -> (00,  01),  (11,  10)
			n=3  -> (000, 001, 011, 010), (110, 111, 101, 100)
			1) prepend 0 to prev nums, 
			2) reverse the order, prepend 1 to prev nums.
			prepend 0 doesn't change the vals, no need to anything.
			prepend 1 will change the num, shift 1 << ith pos. mask | prevNums;
   */
  public List<Integer> grayCode(int n) {
    List<Integer> res = new ArrayList<>();
    res.add(0);
    for (int i = 0; i < n; i++) {
      // shift 1 to ith pos to insert
      int mask = 1 << i;
      // reverse loop stored nums prepend 1 to every number.
      for (int j = res.size() - 1; j >= 0; j--) {
        // insert 1 to ith pos
        int nextCode = res.get(j) | mask;
        res.add(nextCode);
      }
    }
    return res;
  }

}