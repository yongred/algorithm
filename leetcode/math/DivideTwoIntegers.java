/**
29. Divide Two Integers
Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.

Return the quotient after dividing dividend by divisor.

The integer division should truncate toward zero.

Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Note:

Both dividend and divisor will be 32-bit signed integers.
The divisor will never be 0.
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 231 − 1 when the division result overflows.
*/

/**
Solution: divisor += divisor (d * 2) until > dividend; Then calc the rest;
sign: -/- = +; +/+ = +; -/+ = -;
zero: 0/n = 0; n/0 in java it throw exception.
overflow: Use long.

Cannot use *, /, %;
use: + or -;

n/m = count(m + m + m...) times until n<m;
multiply
m + m = 2m, 2m + 2m = 4m; 4m + 4m = 8m; 8m + 8m = 16m;
n = 20m; 20m - 16m = 4m; 20 times;

multiply: 1+1=2; 2+2=4; 4+4=8; 8+8=16;
n=200 m=10; 200/10 = 20
10 + 10 = 20; 20 + 20 = 40; 40 + 40 = 80;
80 + 80 = 160; 160 + 160 = 320 > 200 Stop;
sum = 160;

Next:
200 - 160 = 40; leftover;
n=40; m=10;
multiply: 1+1=2; 2+2=4;
10 + 10 = 20; 20 + 20 = 40; 40+40=80 > 40 Stop.
sum = 40;

Ans = 16 

How to Arrive:
* Since we cannot use * / and %; we can only use - +;
* n/m = count(m + m + m...) times until n<m; This will take O(n) time;
* We can optimize by increase m = m * 2; Stop when it > dividend; Just increase the count = count * 2 also;
Ex: m + m = 2m, 2m + 2m = 4m; 4m + 4m = 8m
* The leftover we just calc it in another recursive call; And add curCount to the return res;
ex: n = 11m, 2^3 = 8, 11-8 = 3m left; 8 + f(3m, m) = 11;
* This question has many corner cases:
	* sign: -/- = +; +/+ = +; -/+ = -; 
		* (check one of them is < 0 and another is > 0);
		* Get the abs val of the 2 nums; use that to calc divide.
		* **Key**: Covert to long first to prevent overflow;
		`long d1 = Math.abs((long)dividend);
      long d2 = Math.abs((long)divisor);`
	* zero: 0/n = 0; n/0; n < m;
		* If abs(dividend) < abs(divisor) ans = 0; 
		* if dividend = 0; return 0;
		* n/0 in java it throw exception, we don't need to do anything.
	* overflow: Use long.
		* Get the long absVals to divide; 
		* After get the long ans; Check Integer overflow; Return  MIN or MAX depend on sign.
	  * no overflow, just return casted (int) -res or (int)res;
	 
* Time: O(lgN); sum * 2 every time until > dividend;
* Space: O(lgN); case 222 / 10; 222-200 = 22; 22-20 =2; 2 < 10 = 0; lg10(n);
*/

class DivideTwoIntegers {

	/**
	 * Solution: divisor += divisor (d * 2) until > dividend; Then calc the rest;
	 * Time: O(lgN); sum * 2 every time until > dividend;
	 * Space: O(lgN); case 222 / 10; 222-200 = 22; 22-20 =2; 2 < 10 = 0; lg10(n);
	 */
  public int divide(int dividend, int divisor) {
    // check negative positive;
    boolean negative = false;
    // -/+ or +/- = neg;
    if (dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0) {
      negative = true;
    }
    // divide the abs val, then add sign at the end.
    // use long prevent overflow int.
    long d1 = Math.abs((long)dividend);
    long d2 = Math.abs((long)divisor);
    // 0 cases. Compare using abs vals. else case -2 / 1 will ret 0;
    if (d1 < d2 || d1 == 0) {
      System.out.println(d1 + " " + d2);
      return 0;
    }
    // divide
    long resLong = helper(d1, d2);
    // check overflow int val.
    if (resLong > Integer.MAX_VALUE) {
      // check negative, return MAX or MIN
      return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    }
    // no overflow, check negative/sign, then convert res to int.
    return negative ? (int) -resLong : (int) resLong;
  }
  
  public long helper(long dividend, long divisor) {
    // base case, until dividend < divisor = 0, or dividend = 0
    if (dividend < divisor || dividend == 0) {
      return 0;
    }
    // divisor add up to dividend
    long sum = divisor;
    // count how many times divisor add up to dividend
    long multiple = 1;
    // divisor * 2 until > dividend.
    while (sum + sum <= dividend) {
      // multiply 2;
      sum += sum;
      // multiple is twice the count when * 2;
      multiple += multiple;
    }
    // calc the rest.
    return multiple + helper(dividend - sum, divisor);
  }
  
}