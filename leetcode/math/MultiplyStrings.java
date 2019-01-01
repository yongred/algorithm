/**
43. Multiply Strings
Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.

Example 1:

Input: num1 = "2", num2 = "3"
Output: "6"
Example 2:

Input: num1 = "123", num2 = "456"
Output: "56088"
Note:

The length of both num1 and num2 is < 110.
Both num1 and num2 contain only digits 0-9.
Both num1 and num2 do not contain any leading zero, except the number 0 itself.
You must not use any built-in BigInteger library or convert the inputs to integer directly.
*/

/**
Solution: Use an Array to store all digits of res.
Ex:
a: 123
b:  45
 
    123
      45
  -----
      15 idx[2+1], [2+1+1]
    10   idx[0+1], [0+1+1]
  05
 -----
    12
  08
04
-----
05535
	
How to Arrive:
* We cannot use Int to store res, then covert the Integer to String, b/c there are going to be huge numbers Len max = 109;
* So we have to store the digits, then append each digit to ResStr.
* We can use a Array to store all res digits. Int[n1 + n2] in len total.
Ex:  (n1)900 * (n2)900 = 810,000; len3 * len3 = len6;
* Each time we calc d1 * d2, we add and update the digits array's positions.
* The positions are: tensIndex = i + j, onesIndex = i + j + 1; Like the Example above.
Ex: 123 * 45; 
3 * 5 = 15, idx2(3) + idx1(5)  = idx3, idx2(3) + idx1(5) + 1 = idx4; 
digits: 00015; index 3,4;
* The carry is in digits[onesIndex] (prev tensPos calculation stored); So the total = d1 * d2 + digits[onesIndex];
* Then we get the current digit by  curDigit = total % 10; And get the carry: carry = total / 10;
* Update the tensIndex and onesIndex positions. tensPos just add the carry to it. OnesPos is the curDigit.
```
digits[tensIndex] += carry;
digits[onesIndex] = curDigit;
```
* After all multiplications. (finish nested loop of n1 and n2), we have all digits stored.
* Convert digits into String. Use a StringBuilder.
* Skip leading 0s. Then append the rest.
* Check 0 + 0 = 0 condition, return 0; Else return sb.toString();
* Time: O(n * m)
* Space: O(n + m);

*/

class MultiplyStrings {
  
  /**
   * Solution: Use an Array to store all digits of res.
   * Time: O(n * m)
   * Space: O(n + m);
   */
  public String multiply(String num1, String num2) {
    // array to store each digit products sum.
    int n1 = num1.length();
    int n2 = num2.length();
    // 900 * 900 = 810,000; len3 * len3 = len6;
    int[] digits = new int[n1 + n2];
    // loop reversely
    for (int i = n1 - 1; i >= 0; i--) {
      int d1 = num1.charAt(i) - '0';
      for (int j = n2 - 1; j >= 0; j--) {
        int d2 = num2.charAt(j) - '0';
        // calc product
        int product = d1 * d2;
        // pos for curProduct in digits.
        int tensIndex = i + j;
        int onesIndex = i + j + 1;
        // get the product + carry.
        int total = product + digits[onesIndex];
        // get tens(carry), and ones(digit)
        int curDigit = total % 10;
        int carry = total / 10;
        // add tens(carry) and update ones digit 
        digits[tensIndex] += carry;
        digits[onesIndex] = curDigit;
      }
    }
    // convert digits to string
    StringBuilder sb = new StringBuilder();
    for (int digit : digits) {
      // check leading 0s, don't add these.
      if (digit == 0 && sb.length() == 0) {
        continue;
      }
      sb.append(digit);
    }
    // check 0+0,
    return (sb.length() == 0) ? "0" : sb.toString();
  }
  
  
}