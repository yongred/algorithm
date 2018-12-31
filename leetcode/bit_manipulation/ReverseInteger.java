/**
7. Reverse Integer
Given a 32-bit signed integer, reverse digits of an integer.

Example 1:

Input: 123
Output: 321
Example 2:

Input: -123
Output: -321
Example 3:

Input: 120
Output: 21
Note:
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
*/

/**
Solution 1: Mod 10 to get digits, Divide x by 10 to update x for next digit. Use long to check overflow.
Ex:
x=123
digit: 3, 2, 1
res: 0;
0 + 3= 3; 3 * 10=30; x=12
30+2=32; 32 * 10= 320; x=1;
320+1= 321; last digit x=0; Done.

How to Arrive:
* To reverse digits of integer, we can think of it as Stack pop from one and push into another Stack.
* We get/pop the digit from x; then add/push that digit into Res.
* We get the digit using `int digit = x % 10;`
* We add digit to Res.
* Then we update the x, remove cur ones digit, so we can get next digit.
* If this is not last digit (x == 0):
	* We shift Res left by 1 digit (res * 10), To open up for next digit push/add.
* **Key**: We need to handle overflows of reversed ans.
* One way to do that is simply using a Long, then compare Res to Int.Max and Int.MIN to see if overflow.
* If overflow return 0; otherwise return (int) Res;

* Time: O(1); Max number is 2^31 -1; lg10(2^31) times about 10 times. 2147483648 have 10 digits.
* Space: O(1);

-----------------------

Solution 2: Same method, but by compare curRes to nextRes to check if overflow
How to Arrive:
* Alternatively, if we not allow to use Long. We can determine if res * 10 is overflow.
* nextRes  = res * 10; that means nextRes / 10 = curRes; 
* So we simple have to check **if (nextRes / 10 == curRes)**; If not it is Overflow.
* Time: O(1); 
* Space: O(1);
*/

class ReverseInteger {

	/**
	 * Solution 2: Same method, but by compare curRes to nextRes to check if overflow
   * Time: O(1)
   * Space: O(1)
   */
	public int reverse(int x) {
    // get digit n % 10; append to another int.
    int res = 0;
    while (x != 0) {
      // get digit n % 10;
      int digit = x % 10;
      // append/push to res;
      res += digit;
      // update x, remove cur ones digit.
      x /= 10;
      // check if this is last digit.
      if (x != 0) {
        int curRes = res;
        // not last digit. Shift res by 10 (1 pos to left)
        res *= 10;
        // check overflow. If not overflow nextRes / 10 should = curRes.
        if (res / 10 != curRes) {
          // overflow, return 0;
          return 0;
        }
      }
    }
    // check overflow
    return res;
  }

  /**
   * Solution 1: Mod 10 to get digits, Divide x by 10 to update x for next digit. Use long to check overflow.
   * Time: O(1); Max number is 2^31 -1; lg10(2^31) times about 10 times. 2147483648 have 10 digits.
   * Space: O(1);
   */
  public int reverse(int x) {
    // get digit n % 10; append to another int.
    long res = 0;
    while (x != 0) {
      // get digit n % 10;
      int digit = x % 10;
      // append/push to res;
      res += digit;
      // update x, remove cur ones digit.
      x /= 10;
      // check if this is last digit.
      if (x != 0) {
        // not last digit. Shift res by 10 (1 pos to left)
        res *= 10;
      }
    }
    // check overflow
    return (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) ? 0 : (int)res;
  }
  
}