/**
8. String to Integer (atoi)
Implement atoi which converts a string to an integer.

The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.

The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.

If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.

If no valid conversion could be performed, a zero value is returned.

Note:

Only the space character ' ' is considered as whitespace character.
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.
Example 1:

Input: "42"
Output: 42
Example 2:

Input: "   -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign.
             Then take as many numerical digits as possible, which gets 42.
Example 3:

Input: "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
Example 4:

Input: "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical 
             digit or a +/- sign. Therefore no valid conversion could be performed.
Example 5:

Input: "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
             Thefore INT_MIN (−231) is returned.
*/
/**
Solution: Handle each case separately in order, From " " to "+,-" to "0-9";
How to Arrive:
* This problem is straight forward, but with alot of Corner Cases;
* **Cases**:
	* Whitespace: Skip.
	* 1st Non-whitespace must be either -, +, or digit.
	* Sign - +: Must follow by a digit. (case "+-123" ret 0);
	* Digits 0-9:
		* Check Overflows 32bits; 2147483647, -2147483648;
**Algorithm**:
* Check str = null or "": Return 0;
* Declare vars: 
	* res = 0; for default 0 for any false cases.
	* Index for iter chars, bool Negative for checking sign - +;
* First Case whitespaces; Skip;
* While next char str[i] is ' ':
	* index++;
* Next Case - +;
* If str[i] = '-' or '+':
	* if str[i] = '-':
		* Negative = true;
	* Index++; to check next char is digit.
* Case Digits 0-9:
* While index < n And str[i] isDigit:
	* get the digit, str[i] - '0';
	* Check Overflow; (32bits 2147483647, -2147483648);
		* if res * 10 > MAX, then next digit will overflow.
			* return MIN for negative, MAX for positive.
		* if res * 10 == MAX; compare digits
			* if MAX % 10 < digit:
				* return MIN for negative, MAX for positive.
		Ex:
		if res * 10 == MAX; case 2147483648/10 = 214748364, 2147483647/10 = 214748364;
    compare digits, 2147483648 % 10 = 8; 2147483647 % 10 = 7; 8>7 OVERFLOW.
		* No overflow:
			* res append the digit by res * 10 + digit;
	* increment index;
* Finished appending all digits:
* If no digit, nothing is appened. Res is init to 0; so it will return 0 if no digit.
* Return either -res (-#) or res (+#);
* Time: O(n)
* Space: O(1)
*/

class StringToInteger {

	/**
	 * Solution: Cleaner version, same logic.
	 * Time: O(n)
	 * Space: O(1)
	 */
	public int myAtoi(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }

    int n = str.length();
    int res = 0;
    // define a index
    int index = 0;
    // define a sign/negative positive.
    boolean negative = false;

    // case " " skip
    while (index < n && str.charAt(index) == ' ') {
      index++;
    }

    // case "-" or "+", should follow by a digit, else its false.
    if (index < n && (str.charAt(index) == '-' || str.charAt(index) == '+')) {
      if (str.charAt(index) == '-') {
        negative = true;
      }
      index++;
    }

    // case digits.
    while (index < n && Character.isDigit(str.charAt(index))) {
      // get digit
      int digit = str.charAt(index) - '0';
      // check overflow; 32bits 2147483647, -2147483648;
      // if res * 10 > MAX, then next digit will overflow.
      if (Integer.MAX_VALUE / 10 < res) {
      	// it's either overflow or -2147483648;
        return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
      }
      // if res * 10 == MAX; case 2147483648/10 = 214748364, 2147483647/10 = 214748364;
      // compare digits, 2147483648 % 10 = 8; 2147483647 % 10 = 7; 8>7 OVERFLOW.
      if (Integer.MAX_VALUE / 10 == res && Integer.MAX_VALUE % 10 < digit) {
      	// it's either overflow or -2147483648;
        return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
      }
      // no overflow, update res by append digit.
      res = res * 10 + digit;
      // index increment.
      index++;
    }

    // finished appending all digits, if no digit, nothing is appened. 
    // res is init to 0; so it will return 0 if no digit.
    return negative ? -res : res;
  }
  
  /**
   * Solution: messy 1st draft. Chars condition checks. All cases.
   * Time: O(n)
   * Space: O(1)
   */
  public int myAtoi(String str) {
    int res = 0;
    int n = str.length();
    boolean negative = false;

    // loop and find substr of num.
    for (int i = 0; i < n; i++) {
      char cur = str.charAt(i);
      // if 1st non whitespace char is not - or digit
      if (!Character.isDigit(cur) && cur != '-' && cur != '+'
          && cur != ' ') {
        return 0;
      }
      // 1st non whitespace is - or +, check following is digit.
      if (cur == '-' || cur == '+') {
        // check next is digit. if not return 0;
        if (i + 1 >= n || !Character.isDigit(str.charAt(i + 1))) {
          // not digit. false, 0;
          return 0;
        }
        // next is digit, continue.
        continue;
      }
      // Found 1st digit.
      if (Character.isDigit(cur)){
        // check if is '-',
        if (i > 0 && str.charAt(i - 1) == '-') {
          negative = true;
        } 
        // append digits
        int val = cur - '0';
        // check if next is digit.
        while (i + 1 < n && Character.isDigit(str.charAt(i + 1))) {
          int digit = str.charAt(i + 1) - '0';
          // check overflow;
          // check if val > MAX/10; Or if val == MAX/10, then we compare digit.
          if (Integer.MAX_VALUE / 10 < val || (Integer.MAX_VALUE / 10 == val
              && Integer.MAX_VALUE % 10 < digit)) {
            // overflow, return either MIN or MAX.
            return (negative) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
          }
          val = val * 10 + digit;
          // increment, check next if is digit.
          i++;
        }
        // appended all digits.
        res = val;
        // no need to continue, we just need the first num in str.
        break;
      }
    }
    // either found all digits or no digits.
    // check negative and return
    return (negative) ? -res : res;
  }
  
}