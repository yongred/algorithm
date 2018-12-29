/**
371. Sum of Two Integers
Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.

Example 1:

Input: a = 1, b = 2
Output: 3
Example 2:

Input: a = -2, b = 3
Output: 1
*/

/**
Solution: Use bit, AND (a & b) find carries 1+1 bits, XOR (a ^ b) find 1+0, 0+1 bits.
Ex:
   0111 (7)
+ 1011  (11)
 10010 (18)
 Carries: (0111 & 1011) << 1 = 0110 = b; (1100 & 0110) << 1 = 1000 = b;
 (1010 & 1000) << 1 = 10000 = b; (0010 & 10000) << 1 = 0 = b;
 Digits: (0111 ^ 1011) = 1100 = a; (1100 ^ 0110) = 1010 = a;
 (1010 ^ 1000) = 0010 = a; (0010 ^ 10000) = 10010 = a;
 ANS = 10010;
 
 How to Arrive:
 * For Addition, we need 2 things the **Carry and the Digits**.
 * For bitwise addition:
 * **Carries**: are 1+1 bit; We can find them using & ope, Then << 1, to carryover to next bit. So 1+1 = **(1 & 1) << 1**;
	 * Ex: 0011 + 0011 = 0110; same as (0011 & 0011) << 1 = 0110;
	 * So 2 bits have carries.
 *  **Digits**: are 1+0, 0+1 bits, ignore 0+0=0 no change. We are interested in changed bits.
	 *  1+0 = 1, 0+1=1; Same as **(1 ^ 0 = 1), (0 ^ 1 = 1)**;
	 *  Ex: 0100 + 1000 = 1100; (0100 ^ 1000) = 1100; Same;
 * Now 0110 = b carries and 1100 = a digits, are used for next bit ope, just like addition.
 * We repeat until there is no more carry. (like addition, carries will eventually all become part of digits);
* Return digits (a) when carry (b) = 0;
* 
* Time: O(1); Shift until no more carry, which is constant.
* Space: O(1);

-----------

Subtraction:
Ex:
X     Y     Diff     Borrow
0     0     0     0
0     1     1     1
1     0     1     0
1     1     0     0
Similar to Addition, instead of carry, we find borrowed bits.
* Borrow bits are a's 0s, and we don't want 1s b/c 1s don't need borrow.
* We want to know a's 0s and b's 1s; b/c 0 -1 needs borrow.
* We can ~a, flip bits now 0s becomes 1s, and (~a) & b to find 0 - 1 bits;
Ex: 100 - 011; ~100 = 011, (011 & 011)  = 011 borrowed bits.
* Now we need to find where the diffs is 1; 0-1, 1-0; those gives us 1.
* Those bits need to subtract their bits that borrowes to other.
* Then we left shift borrowes (~a & b) << 1; B/c **borrowed 1s needs to be subtracted from original borrowing bits**.
Ex: 100 - 011; borrowes from bit 2,4; 011 is borrowed bits << 1 = 110 to be subtracted.


*/

class SumOfTwoIntegers {

	/**
	 * Solution: Use bit, AND (a & b) find carries 1+1 bits, XOR (a ^ b) find 1+0, 0+1 bits.
	 * Time: O(1); Shift until no more carry, which is constant.
	 * Space: O(1);
	 */
  public int getSum(int a, int b) {
    // one num is 0, n + 0 = n;
    if (a == 0 || b == 0) {
      return b == 0 ? a : b;
    }
    // while there is still carry
    while (b != 0) {
      // use b as carries 1+1 (1&1 << 1)
      int carry = (a & b);
      // a as digits (0+1, 1+0) (0^1, 1^0);
      a = (a ^ b);
      b = carry << 1;
    }
    return a;
  }

  /**
   * Subtraction: Find borrowed bits.
   * Time: O(n)
   * Space: O(1)
   */
  public static int subtract(int a, int b) {
    while (b != 0) {
      // borrow when a = 0, b = 1; 0-1;
      int borrow = (~a & b);
      // diff of 0-1, 1-0; those gives us 1.  
      a = a ^ b;
      // Borrow is shifted by one borrowed 1s needs to be subtracted from original borrowing bits.
      b = borrow << 1;
    }
    return a;
  }


}