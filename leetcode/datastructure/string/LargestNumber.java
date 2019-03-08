/**
179. Largest Number
Given a list of non negative integers, arrange them such that they form the largest number.

Example 1:

Input: [10,2]
Output: "210"
Example 2:

Input: [3,30,34,5,9]
Output: "9534330"
Note: The result may be very large, so you need to return a string instead of an integer.
*/

/*
Solution: Custom comparator to sort the nums in a:b or b:a combo;
Ex:
[3, 30, 34, 5, 9]
330 > 303; [3,30]
343 > 334; [34, 3, 30]
534 > 345; [5, 34, 3, 30]
95 > 59; [9, 5, 34, 3, 30];

* We just need to find a way to sort the nums in DESC order of larger combo in front;
* Ex: "3:31" > "31:3";
* In Java we can compare strings like this: "123" > "122"; "122" == "122";
* So just create a custom comparator Lambda to sort the Array nums;
* First, we need to convert nums into Strings of nums;
* Then, Sort by comparing "B:A".compareTo("A:B"); DESC;
* Append Sorted numStrs into Res string;
**Tricky Case**: "000" can be form by [0,0,0] input;
* Check if 1st char is 0; if is return "0"; B/c if first/largest == 0, then the following are all 0s;
* Return res;

* Time: O(N Lg N);
* Space: O(n);
*/
class LargestNumber {
  
  /**
  Solution: Custom comparator to sort the nums in a:b or b:a combo;
	* Time: O(N Lg N);
	* Space: O(n);
	*/
  public String largestNumber(int[] nums) {
    if (nums == null || nums.length == 0) {
      return "";
    }
    // convert nums to string.
    String[] numStrs = new String[nums.length];
    for (int i = 0; i < nums.length; i++) {
      numStrs[i] = String.valueOf(nums[i]);
    }
    // DESC sort by str value; "123" > "122" in string compare;
    Arrays.sort(numStrs, (a, b) -> {
      String ab = a + b;
      String ba = b + a;
      // DESC
      return ba.compareTo(ab);
    });
    // check leading 0, like "0000" case; return 0;
    // if first/largest == 0, then the following are all 0s;
    if (numStrs[0].equals("0")) {
      return "0";
    }
    StringBuilder sb = new StringBuilder();
    for (String str : numStrs) {
      sb.append(str);
    }
    return sb.toString();
  }
  
  
}