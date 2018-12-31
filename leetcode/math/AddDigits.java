/**
258. Add Digits
Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

Example:

Input: 38
Output: 2 
Explanation: The process is like: 3 + 8 = 11, 1 + 1 = 2. 
             Since 2 has only one digit, return it.
Follow up:
Could you do it without any loop/recursion in O(1) runtime?
*/

/**
Solution 1: Divide num, Mod to get digits, add to Sum, update num = sum;
Ex:
n= 942
num: 
942, 94, 9, 0; 
15, 1, 0;
7 ANS.

sum: 0, 15 > 10, 7 < 10 Done;
0+2=2, 2+4=6, 6+9=15;
0+5=6, 6+1=7;

Ans = 7;
How to Arrive:
* They gave us the condition, whch is when num < 10 we can stop.
* They want the sum of the curDigits, so we will need a var to store the curSum.
* To calc cur sum, we need to get the digits. Using num % 10; then num /= 10 to get next digit. Until num is 0;
* When num is 0 we have the sum for the digits.
* We update num = curSum. To repeat the process of newNum.
* If newNum < 10, we can stop.
* Return num;
* 
* Time: O(1); MAX INT is 2^31, about 10 division loop.
* Space: O(1)

--------------

Solution 2: Use math fact, any number that is divisible by 9, the sum of the digits in the number is also divisible by 9.
Ex:
n=18
1+8 = 9;
18 % 9 =0; Ans = 9;

n=21
2+1 = 3;
21 % 9 =3; Ans = 3;

ex1:
n = 8136;
8+1+3+6 = 18;
1+8=9;
8136 % 9 = 0;
Ans = 9;

ex2:
n= 8139
8+1+3+9 = 21;
2+1=3;
8139 % 9 = 3;
Ans = 3;

How to Arrive:
* The Ans is between 0-9;
* Math Fact: If n % 9 = 0, then digitsOfN % 9 = 0; 
* Any num divisible by 9, the sum of num's digits is also divisible by 9.
* **Key**: Therefore every num is either divisible by 9, or Not.
	* If divisible by 9, n % 9 = 0; Then its digits sum % 9 = 0; Ans = 9;
	* If not divisible by 9, Then the ans lies between 1-8; Which is the same as (n % 9);
	Ex: 81 % 9 = 0, (8+1=9) Ans = 9;
	82 % 9 = 1, (8+2=10, 0+1=1) Ans = 1;
	83 % 9 = 2, (8+3=11, 1+1=2) Ans = 2;
	... 89 % 9 = 8, (8+9=17, 1+7=8) Ans = 8;
	90 % 9 = 0, (9+0 = 9) Ans = 9;
* Time: O(1)
* Space: O(1)
*/

class AddDigits {
  
  /**
   * Solution 1: Divide num, Mod to get digits, add to Sum, update num = sum;
   * Time: O(1); MAX INT is 2^31, about 10 division loop.
   * Space: O(1)
   */
  public int addDigits(int num) {
    // divide util num < 10;
    while (num >= 10) {
      int sum = 0;
      // get num's digits and add to res.
      while (num != 0) {
        // get ones digit.
        int digit = num % 10;
        // add digit to res
        sum += digit;
        num /= 10;
      }
      // update num to digit sum;
      num = sum;
    }
    return num;
  }
  
  /**
   * Solution 2: Use math fact: Any number that is divisible by 9, the sum of the digits in the number is also divisible by 9.
   * Time: O(1)
   * Space: O(1)
   */
  public int addDigits(int num) {
    if (num == 0) {
      return 0;
    }
    if (num % 9 == 0) {
      return 9;
    } else {
      return num % 9;
    }
  }
  
}