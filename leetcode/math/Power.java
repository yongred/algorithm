/**
50. Pow(x, n)
Implement pow(x, n), which calculates x raised to the power n (xn).

Example 1:

Input: 2.00000, 10
Output: 1024.00000
Example 2:

Input: 2.10000, 3
Output: 9.26100
Example 3:

Input: 2.00000, -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25
Note:

-100.0 < x < 100.0
n is a 32-bit signed integer, within the range [−231, 231 − 1]
*/

/**
Solution: recursive divide, x^n = x^(n/2) * x^(n/2) even; x^n = x * x^(n/2) * x^(n/2) for odd n.
Ex:
x^n = x^(n/2) * x^(n/2) for even n
x^n = x * x^(n/2) * x^(n/2) for odd n
x^4 = x * x * x * x = x^2 * x^2;
x^5 = x * x * x * x * x = x * x^2 * x^2;

How to Arrive:
* Check the sign (negative or positive) of exponent;
* Base Case: n = 0; return 1; power of 0 = 1;
* Recursive case:
	* Cache save x^n/2 val for optimization. halfVal = pow(x, n/2);
	* Cache O(lgN), if not then time T(n^lgn) = O(n);
	* If even: 
		* return halfVal * halfVal;
	* if Odd:
		* return x * halfVal * halfVal;
* Check sign:
	* if negative return (1 / res )
	* positive return res;

* Time: O(lgN); cache O(lgN), if not then time recursive tree height = lgn, width = n, T(n^lgn) = O(n);
* Space: O(lgN)

-----------
For iterative:
2^6
res: 1, 4, 64
x: 2, 4, 16, 256
n: 6, 3, 1, 0
6 even, x: 2 * 2= 4; n: 6/2=3
3 odd, res: 1 * 4= 4; x: 4 * 4= 16; n: 3/2=1;
1 odd, res: 4 * 16= 64; x: 16 * 16 = 256; n: 1/2 = 0 DONE;
ANS = 64;
*/

class Power {

	/**
	 * Solution: recursive divide, x^n = x^(n/2) * x^(n/2) even; x^n = x * x^(n/2) * x^(n/2) for odd n
	 * Time: O(lgN); cache O(lgN), if not then time recursive tree height = lgn, width = n, T(n^lgn) = O(n);
	 * Space: O(lgN)
	 */
  public double myPow(double x, int n) {
    boolean sign = (n < 0) ? true : false;
    double res = helper(x, n);
    // check sign
    return (sign) ? 1 / res : res;
  }
  
  public double helper(double x, int n) {
    // base case power of 0 = 1; prevent 0/2 exception.
    if (n == 0) {
      return 1;
    }
    // cache O(lgN), if not then time T(n^lgn) = O(n);
    double halfPower = helper(x, n / 2);
    if (n % 2 == 0) {
      // even x^n = x^(n/2) * x^(n/2)
      return  halfPower * halfPower;
    } else {
      // odd x^n = x * x^(n/2) * x^(n/2)
      return x * halfPower * halfPower;
    }
  }

  /**
   * Solution: iterative, same logic
   * Time: O(lgn)
   * Space: O(1)
   */
  public double myPow(double x, int n) {
    if (n == 0) {
      return 1;
    }
    double res = 1;
    boolean sign = n < 0 ? true : false;
    // prevent negative MIN overflow.
    long exp = Math.abs((long)n);
    while (exp > 0) {
      // odd
      if (exp % 2 != 0) {
        // one more x; x * x^n/2 * x^n/2;
        // last exp is always 1, so res will * all prev calculated x;
        res *= x;
      }
      // x * x = x^2; x^2 * x^2 = x^4; x^4 * x^4 = x^8;
      x = x * x;
      // half the exp
      exp /= 2;
    }
    return (sign) ? 1 / res : res;
  }
  
}