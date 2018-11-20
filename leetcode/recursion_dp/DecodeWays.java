/**
91. Decode Ways
A message containing letters from A-Z is being encoded to numbers using the following mapping:

'A' -> 1
'B' -> 2
...
'Z' -> 26
Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:

Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).
Example 2:

Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
*/

/**
Solution: Recursive
How to Arrive:
* What are the cases?
	* 1. empty string "" is decoded into ""; So 1 way.
	* 2. If **starts 0 cannot decode**. So 0 way.
	* 3. count cur char as single digit 1-9. Ex: "12345" => 1 "A" + decode("2345");
	* 4, Or count cur char as double digit with the next char, Ex: "12345", 12 "L" + decode("345"); Only if the combo is >= 10, <= 26;
	* So, the cur char can be counted as 2 ways. If cur is 1-9, then +1, 1 way. If cur + next digit is 10-26, then +1 again, 2ways total.
* Notice It's like a path way then split into 2 paths, It splits if 2 digits combo is 10-26. And 0 is like a dead end to close out a path.
* Ex: 10345; Path1: '1' => '0345' close; Path2: '10' => '345' 1 path; only 1 path left.
* So, **Only when encounters 2 digit 10-26** we +1 to total ways so far. B/c path split.
* Ex: 3416; 3 => 416, 1way 1digit; 34=>16; > 26, 0 ways 2digit. 
	* Then 416; 4 => 16, 1way 1digit continue with last; 41 => 6; > 26, 0 ways 2digit.
	* Then 16; 1 => 6, 1way 1digit contination; 16 < 26 & 16>1; 1 ways 2digit. Split another Path. 1+1 = 2ways total. 
	* Then 6, 6 => "", "" is 1way continuation; "" => "", 1 way continue last; 2 ways total.
* So, we need to pass down "12345" => 1 "A" + decode("2345") Or 12 "L" + decode("345"), until it becomes "" return 1 or encounter starting 0.
* Time: O(2^n);
* Space: O(n);

Solution: DP
How to Arrive:
* If we know the prev Paths total # ways, we can calc cur total ways.
* **Key**: think of cur pos as Which Paths Leads to here.
* Ex: "12(3)45" cur is 3. If we count as 1 digit just '3'. Which becomes 'C'.
* Count AS 1 Digit: 1,2,3 "DB(C)" + decode("45"), OR 12,3 "L(C)" + decode("45");
* As we can see, There are 2 paths to Count '3' as 'C'. 
* The prev pos in this case is at number 2. dp[i-1]; The str is '12', Which is 2 path/ways. dp[i] = dp[i-1]; dp[i] = 2;
* "1(23)45", 1,23 "A(W)" + decode("45"); Only 1 way.
* The prev Num in this case is 1, dp[i-2], str= '1', the paths for '1' is 1 way. 
* Now add ways of '12' and ways of '1' together
* dp[i] = 2 + 1 = 3; 
* Time: O(n);
* Space: O(n);

Solution: DP optimize space:
* All we need is 2 values: 
	* **prev 1 pos total ways** and **Prev 2 pos total ways.**;
	* if curDigit is between 1-9, assign prev 1 pos total ways to cur total.
	* if prevDigit combine curDigit is 10-26, add prev 2 pos total ways to cur total.
* Then in the end, return the last calc ways.
* Time: O(n);
* Space: O(1);
*/

class DecodeWays {

	/**
		* Time: O(n);
		* Space: O(1);
   */
	public int numDecodingsDPOptimized(String s) {
    if (s == null) {
      return 0;
    } 
    // digit2ways store # ways count as 2 digit num. Ex: 12(34) '12' is prev 2 pos. 
    // "" is 1.
    int digit2ways = 1;
    // digit1ways store # ways count as 1 digit num. Ex: 123(4) '123' is prev 1 pos.
    // cannot decode 0 or 0123, str start w/ 0.
    int digit1ways = s.charAt(0) == '0' ? 0 : 1;
    
    for (int i = 2; i <= s.length(); i++) {
      // 1 digit
      int ones = Integer.valueOf(s.substring(i - 1, i));
      // 2 digits
      int tenth = Integer.valueOf(s.substring(i - 2, i));
      
      int curTotal = 0;
      // ex: "12(3)45" cur is 3; 
      // Count AS 1 Digit: 1,2,3 "DB(C)" + decode("45"), OR 12,3 "L(C)" + decode("45"); 2 ways.
      if (ones >= 1 && ones <= 9) {
        curTotal = digit1ways;
      }
      // if 2 char combo num is 10-26, start prev2.
      // ex: "1(23)45", 1,23 "A(W)" + decode("45"); Only 1 way;
      if (tenth >= 10 && tenth <= 26) {
        curTotal += digit2ways;
      }
      // update for next iteration. prev 2 pos # ways.
      digit2ways = digit1ways;
      digit1ways = curTotal;
    }

    return digit1ways;
  }

	/**
	 * Solution: DP
	 * Time: O(n)
	 * Space: O(n)
	 */
	public int numDecodingsDP(String s) {
    if (s == null) {
      return 0;
    }
    int [] dp = new int[s.length() + 1];
    // "" is 1.
    dp[0] = 1;
    // cannot decode 0 or 0123, str start w/ 0.
    dp[1] = s.charAt(0) == '0' ? 0 : 1;
    
    for (int i = 2; i <= s.length(); i++) {
      // 1 digit
      int ones = Integer.valueOf(s.substring(i - 1, i));
      // 2 digits
      int tenth = Integer.valueOf(s.substring(i - 2, i));
      // starting with prev1
      // ex: "12345" => "A" + decode("2345"); 
      if (ones >= 1 && ones <= 9) {
        dp[i] = dp[i - 1];
      }
      // if 2 char combo num is 10-26, start prev2.
      // ex: "12345", 12 "L" + decode("345"); 
      if (tenth >= 10 && tenth <= 26) {
        // "A" + decode("2345") Or(+) "L" + decode("345"); Total
        dp[i] += dp[i - 2];
      }
    }
    return dp[s.length()];
  }

  /**
   * Solution: brute force recursive.
   * Time: O(2^n);
   * Space: O(n);
   */
  public int numDecodings2(String s) {
    if (s == null) {
      return 0;
    }
    // cannot decode 0 or 0123, str start w/ 0.
    if (s.length() >= 1 && s.charAt(0) == '0') {
      return 0;
    }
    if (s.length() == 0 || s.length() == 1) {
      return 1;
    }
    
    // find 1 digit start leads to # of count.
    // ex: "12345" => "A" + decode("2345");
    int count = numDecodings2(s.substring(1));
    // find 2 digit 10-26 res, ex: "12345", "12" = "L"
    int digit2 = Integer.valueOf(s.substring(0, 2));
    // if 2 char combo num is 10-26, then add
    // ex: "12345", "A" + decode("2345") OR "L" + decode("345");
    if (digit2 <= 26) {
      count += numDecodings2(s.substring(2));
    }
    
    return count;
  }
}