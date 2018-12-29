/**
201. Bitwise AND of Numbers Range
Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.

Example 1:

Input: [5,7]
Output: 4
Example 2:

Input: [0,1]
Output: 0
*/

/**
Solution: Compare and Right Shift away rightmost bits.
Ex:
Even and Odd bits' 1st bit is diff. Even (0), odd (1);
1110001
1110010
1110011
(11100) common
1110100
1110101
1110110
1110111
(1110) common
1111000
(111) common
Left bits are consistant. Right bits changes.

How to Arrive:
* Even and Odd number's 1st bit is different. 0001, 0010, 0011, 0100;
* Even (0) and Odd (1) for 1st bit.
* From m to n, if m != n; there is atleast 1 odd and 1 even num. So 1st bit is not ==;
* Also, rightmost bits are the ones to change every increment. Like decimal numbers.
Ex: 187, 188, 189, 190; 18 is consistent b/c ones digit changes first, then tens digit, then hundreds digit.
Binary is the same: 1101, 1110, 1111;
* So what we have to do is Remove the rightmost bits (right shift) 1 by 1 until m == n;
Ex:
1110100 => 111010 => 11101
1110101 => 111010 => 11101
1110110 => 111011 => 11101
1110111 => 111011 => 11101
Same as shifting first num 1110100 and last num 1110111 twice >>;
* We count how many right shifts it takes. After we found the common left bits.
* We just left shift back that many times to the found common left bits num;
* Time: O(lgN); We shift/divideBy 2, until m = n;
* Space: O(1);

*/

class BitwiseAndNumbersRange {
  
  /**
   * solution: Compare and Right Shift away rightmost bits.
	 * Time: O(lgN); We shift/divideBy 2, until m = n;
	 * Space: O(1);
	 */
  public int rangeBitwiseAnd(int m, int n) {
    // keep right shift, (remove the rightmost bits) until m == n;
    int shifted = 1;
    while (m != n) {
      // remove/shfit rightmost bits, roughly same as divide by 2;
      m >>= 1;
      n >>= 1;
      // count the shifted bits, same as *= 2, left shift back;
      shifted <<= 1;
    }
    // rest bits * 2^shiftedCount; same as m <<= shifted times.
    // left shift back shifted times.
    return m * shifted;
  }
  
}